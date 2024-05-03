package site.mutopia.server.spotify;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Base64;

@Component
@Slf4j
public class SpotifyClientManager {

    @Value("${spotifies.client.id}")
    private String clientId;

    @Value("${spotifies.client.secret}")
    private String clientSecret;

    @Getter
    private WebClient spotifyClient;

    @PostConstruct
    public void initWebClient() {
        this.spotifyClient = WebClient.builder()
                .baseUrl("https://api.spotify.com/v1")
                .defaultHeader("Authorization", "Bearer " + getAccessToken())
                .build();
    }

    private String getAccessToken() {

        String client64 = Base64.getEncoder().encodeToString((clientId+":"+clientSecret).getBytes());
        String auth = "Basic " + client64;


        WebClient webClient = WebClient.builder()
                .baseUrl("https://accounts.spotify.com/api")
                .defaultHeader("Authorization", auth)
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

        log.info("Access token: {}", authResponse.access_token);
        return authResponse.access_token;
    }

    private static class AuthResponse {
        @JsonProperty("access_token")
        String access_token;

        @JsonProperty("token_type")
        String token_type;

        @JsonProperty("expires_in")
        String expires_in;
    }
}
