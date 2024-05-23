package site.mutopia.server.domain.auth.controller;

import lombok.*;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import site.mutopia.server.domain.auth.annotation.LoginUser;
import site.mutopia.server.domain.user.entity.UserEntity;

@RestController
@RequiredArgsConstructor
public class OAuth2TokenController {

    private final OAuth2AuthorizedClientService authorizedClientService;

    @GetMapping("/oauth2/token/google")
    public GetGoogleTokenDto getToken(@LoginUser UserEntity loggedInUser) {
        OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient("google", loggedInUser.getId());

        // TODO: exception 정의
        if (client == null) {
            throw new RuntimeException("Error: Authorized client not found");
        }

        return GetGoogleTokenDto.builder()
                .accessToken(client.getAccessToken().getTokenValue())
                .refreshToken(client.getRefreshToken().getTokenValue())
                .build();
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    public static class GetGoogleTokenDto {
        private String accessToken;
        private String refreshToken;
    }
}