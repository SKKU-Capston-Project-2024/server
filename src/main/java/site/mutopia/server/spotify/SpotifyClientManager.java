package site.mutopia.server.spotify;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Base64;

@Component
@Slf4j
@Getter
public class SpotifyClientManager {

    @Value("${spotify.client.id}")
    private String clientId;

    @Value("${spotify.client.secret}")
    private String clientSecret;

    @Value("${spotify.redirect.uri}")
    private String redirectUri;

    private String accessToken = null;
    private LocalDateTime accessTokenExpiredTime = null;

    @Getter
    private WebClient spotifyClient;


    @PostConstruct
    public void initWebClient() {
        this.spotifyClient = WebClient.builder()
                .baseUrl("https://api.spotify.com/v1")
                .filter(authHeaderFilter())
                .build();
        this.accessToken = getAccessToken();
    }

    public String getAuthorizationHeader() {
        String client64 = Base64.getEncoder().encodeToString((clientId + ":" + clientSecret).getBytes());
        String auth = "Basic " + client64;
        return auth;
    }

    private ExchangeFilterFunction authHeaderFilter() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
                    // 요청 전에 헤더를 설정
                    ClientRequest request = ClientRequest.from(clientRequest)
                            .headers(headers -> headers.setBearerAuth(getAccessToken()))
                            .build();
                    return Mono.just(request);
                });
    }

    private String getAccessToken() {

        if (accessTokenExpiredTime != null) {
            if (LocalDateTime.now().isBefore(accessTokenExpiredTime.minusMinutes(10))) {
                return accessToken;
            }
        }

        WebClient webClient = WebClient.builder()
                .baseUrl("https://accounts.spotify.com/api")
                .defaultHeader("Authorization", getAuthorizationHeader())
                .build();
        AuthResponse authResponse = webClient.post()
                .uri("/token")
                .body(BodyInserters.fromFormData("grant_type", "client_credentials"))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(AuthResponse.class).block();

        if (authResponse == null) {
            throw new RuntimeException("Failed to get access token");
        }

        accessToken = authResponse.accessToken;
        accessTokenExpiredTime = LocalDateTime.now().plusSeconds(authResponse.expiresIn);
        return accessToken;
    }

    @Getter
    public static class AuthResponse {
        @JsonProperty("access_token")
        private String accessToken;

        @JsonProperty("refresh_token")
        private String refreshToken;

        @JsonProperty("expires_in")
        private Long expiresIn;

        @JsonProperty("token_type")
        private String tokenType;

        @JsonProperty("scope")
        private String scope;
    }

    public AuthResponse exchangeCodeForToken(String code) {
        return WebClient.builder()
                .baseUrl("https://accounts.spotify.com/api")
                .defaultHeader("Authorization", getAuthorizationHeader())
                .build()
                .post()
                .uri("/token")
                .body(BodyInserters.fromFormData("code", code)
                        .with("redirect_uri", redirectUri)
                        .with("grant_type", "authorization_code"))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(AuthResponse.class)
                .onErrorResume(error -> {
                    log.error("Error exchanging code for token", error);
                    return Mono.empty();
                }).block();
    }
}
