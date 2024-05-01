package site.mutopia.server.domain.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UriComponentsBuilder;
import site.mutopia.server.domain.auth.jwt.TokenProvider;

import java.io.IOException;


@Component
@RequiredArgsConstructor
public class AuthSuccessHandler implements AuthenticationSuccessHandler {

    private final TokenProvider tokenProvider;
    private static final String URI = "http://naver.com";


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
            String accessToken = tokenProvider.generateAccessToken(authentication);

            String redirectUrl = UriComponentsBuilder.fromUriString(URI)
                    .queryParam("accessToken", accessToken)
                    .build().toUriString();
            response.sendRedirect(redirectUrl);
    }
}
