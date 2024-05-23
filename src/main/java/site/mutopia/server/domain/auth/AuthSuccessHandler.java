package site.mutopia.server.domain.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;
import site.mutopia.server.domain.auth.entity.GoogleTokenEntity;
import site.mutopia.server.domain.auth.entity.GoogleTokenType;
import site.mutopia.server.domain.auth.jwt.TokenProvider;
import site.mutopia.server.domain.auth.repository.GoogleTokenRepository;
import site.mutopia.server.domain.user.entity.UserEntity;
import site.mutopia.server.domain.user.exception.UserNotFoundException;
import site.mutopia.server.domain.user.repository.UserRepository;

import java.io.IOException;


@Component
@RequiredArgsConstructor
public class AuthSuccessHandler implements AuthenticationSuccessHandler {

    private final TokenProvider tokenProvider;
    private final GoogleTokenRepository googleTokenRepository;
    private final OAuth2AuthorizedClientService authorizedClientService;
    private final UserRepository userRepository;

    @Value("${client-info.host}")
    private String URI;


    @Override
    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if ("google".equals(((OAuth2AuthenticationToken) authentication).getAuthorizedClientRegistrationId())) {
            saveGoogleTokens(authentication);
        }

        String accessToken = tokenProvider.generateAccessToken(authentication);

        String redirectUrl = UriComponentsBuilder.fromUriString(URI)
                .queryParam("accessToken", accessToken)
                .build().toUriString();
        response.sendRedirect(redirectUrl);
    }

    private void saveGoogleTokens(Authentication authentication) {
        OAuth2User oAuth2User = ((OAuth2AuthenticationToken) authentication).getPrincipal();
        String userId = oAuth2User.getAttribute("userId");
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found. userId: " + userId + " does not exist."));

        OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient("google", user.getId());
        OAuth2AccessToken accessToken = client.getAccessToken();
        OAuth2RefreshToken refreshToken = client.getRefreshToken();

        GoogleTokenEntity accessTokenEntity = GoogleTokenEntity.builder()
                .tokenType(GoogleTokenType.ACCESS)
                .expiresAt(accessToken.getExpiresAt())
                .issuedAt(accessToken.getIssuedAt())
                .user(user)
                .tokenValue(accessToken.getTokenValue()).build();

        GoogleTokenEntity refreshTokenEntity = GoogleTokenEntity.builder()
                .tokenType(GoogleTokenType.REFRESH)
                .expiresAt(refreshToken.getExpiresAt())
                .issuedAt(refreshToken.getIssuedAt())
                .user(user)
                .tokenValue(refreshToken.getTokenValue()).build();

        googleTokenRepository.save(accessTokenEntity);
        googleTokenRepository.save(refreshTokenEntity);
    }
}
