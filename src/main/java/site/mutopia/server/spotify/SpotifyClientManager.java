package site.mutopia.server.spotify;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import site.mutopia.server.spotify.dto.playlist.*;
import site.mutopia.server.spotify.dto.profile.SpotifyUserProfile;
import site.mutopia.server.spotify.dto.recommendation.RecommendationsDto;
import site.mutopia.server.spotify.exception.SpotifyAccessTokenExpiredException;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;

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

    public String getAuthorizationBasicHeader() {
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
                .defaultHeader("Authorization", getAuthorizationBasicHeader())
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

    public String refreshAccessToken(String refreshToken) {
        AuthResponse authResponse = WebClient.builder()
                .baseUrl("https://accounts.spotify.com/api")
                .defaultHeader("Authorization", getAuthorizationBasicHeader())
                .defaultHeader("content-type", "application/x-www-form-urlencoded")
                .build()
                .post()
                .uri("/token")
                .body(BodyInserters.fromFormData("grant_type", "refresh_token")
                        .with("refresh_token", refreshToken))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(AuthResponse.class)
                .onErrorResume(WebClientResponseException.class, ex -> {
                    log.error("Error refreshing access token: " + ex.getStatusCode() + " " + ex.getResponseBodyAsString());
                    return Mono.empty();
                })
                .block();

        if (authResponse == null || authResponse.getAccessToken() == null) {
            throw new RuntimeException("Failed to refresh access token");
        }

        return authResponse.getAccessToken();
    }


    @Getter
    @ToString
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
                .defaultHeader("Authorization", getAuthorizationBasicHeader())
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

    public String getAuthorizationBearerHeader(String accessToken) {
        return "Bearer " + accessToken;
    }

    public SpotifyUserProfile getCurrentUserProfile(String accessToken) {
        return WebClient.builder()
                .baseUrl("https://api.spotify.com/v1")
                .defaultHeader("Authorization", getAuthorizationBearerHeader(accessToken))
                .build()
                .get()
                .uri("/me")
                .retrieve()
                .onStatus(httpStatus -> httpStatus == HttpStatus.UNAUTHORIZED, response ->
                        response.bodyToMono(String.class).flatMap(errorBody -> Mono.error(new SpotifyAccessTokenExpiredException(errorBody)))
                )
                .bodyToMono(SpotifyUserProfile.class)
                .doOnError(WebClientResponseException.class, ex -> {
                    log.error("Error: " + ex.getStatusCode() + " " + ex.getResponseBodyAsString());
                }).block();
    }

    public SpotifyPlaylist createPlaylist(String spotifyUserId, String accessToken, String name, String description, boolean isPublic) {
        SpotifyPlaylistCreateReq playlistRequest = SpotifyPlaylistCreateReq.builder()
                .name(name)
                .description(description)
                .isPublic(isPublic)
                .build();

        return WebClient.builder()
                .baseUrl("https://api.spotify.com/v1")
                .defaultHeader("Authorization", getAuthorizationBearerHeader(accessToken))
                .build()
                .post()
                .uri("/users/" + spotifyUserId +"/playlists")
                .body(Mono.just(playlistRequest), SpotifyPlaylistCreateReq.class)
                .retrieve()
                .onStatus(httpStatus -> httpStatus == HttpStatus.UNAUTHORIZED, response ->
                        response.bodyToMono(String.class).flatMap(errorBody -> Mono.error(new SpotifyAccessTokenExpiredException(errorBody)))
                )
                .bodyToMono(SpotifyPlaylist.class)
                .doOnError(WebClientResponseException.class, ex -> {
                    log.error("Error: " + ex.getStatusCode() + " " + ex.getResponseBodyAsString());
                })
                .block();
    }

    public SpotifyPlaylistAddTracksRes addTracksToPlaylist(String playlistId, String accessToken, List<String> songIds, Integer position) {
        List<String> uris = songIds.stream().map(songId -> "spotify:track:" + songId).toList();

        SpotifyPlaylistAddTracksReq addTracksRequest = SpotifyPlaylistAddTracksReq.builder()
                .uris(uris)
                .position(position)
                .build();

        return WebClient.builder()
                .baseUrl("https://api.spotify.com/v1")
                .defaultHeader("Authorization", getAuthorizationBearerHeader(accessToken))
                .defaultHeader("Content-Type", "application/json")
                .build()
                .post()
                .uri("/playlists/" + playlistId + "/tracks")
                .body(Mono.just(addTracksRequest), SpotifyPlaylistAddTracksReq.class)
                .retrieve()
                .onStatus(httpStatus -> httpStatus == HttpStatus.UNAUTHORIZED, response ->
                        response.bodyToMono(String.class).flatMap(errorBody -> Mono.error(new SpotifyAccessTokenExpiredException(errorBody)))
                )
                .bodyToMono(SpotifyPlaylistAddTracksRes.class)
                .doOnError(WebClientResponseException.class, ex -> {
                    log.error("Error: " + ex.getStatusCode() + " " + ex.getResponseBodyAsString());
                })
                .block();
    }

    public SpotifyPlaylistDetails getPlaylistDetails(String playlistId, String accessToken) {
        return WebClient.builder()
                .baseUrl("https://api.spotify.com/v1")
                .defaultHeader("Authorization", getAuthorizationBearerHeader(accessToken))
                .defaultHeader("Content-Type", "application/json")
                .build()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/playlists/" + playlistId)
                        .build())
                .retrieve()
                .onStatus(httpStatus -> httpStatus == HttpStatus.UNAUTHORIZED, response ->
                        response.bodyToMono(String.class).flatMap(errorBody -> Mono.error(new SpotifyAccessTokenExpiredException(errorBody)))
                )
                .bodyToMono(SpotifyPlaylistDetails.class)
                .doOnError(WebClientResponseException.class, ex -> {
                    log.error("Error: " + ex.getStatusCode() + " " + ex.getResponseBodyAsString());
                })
                .block();
    }

    public RecommendationsDto getRecommendations(List<String> songIds, String accessToken) {

        return WebClient.builder()
                .baseUrl("https://api.spotify.com/v1")
                .defaultHeader("Authorization", getAuthorizationBearerHeader(accessToken))
                .defaultHeader("Content-Type", "application/json")
                .build()
                .get()
                .uri(uriBuilder -> uriBuilder.path("/recommendations")
                        .queryParam("seed_tracks", String.join(",", songIds))
                        .build())
                .retrieve()
                .onStatus(httpStatus -> httpStatus == HttpStatus.UNAUTHORIZED, response ->
                        response.bodyToMono(String.class).flatMap(errorBody -> Mono.error(new SpotifyAccessTokenExpiredException(errorBody)))
                )
                .bodyToMono(RecommendationsDto.class)
                .doOnError(WebClientResponseException.class, ex -> {
                    log.error("Error: " + ex.getStatusCode() + " " + ex.getResponseBodyAsString());
                })
                .block();
    }
}
