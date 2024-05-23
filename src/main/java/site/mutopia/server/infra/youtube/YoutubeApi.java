package site.mutopia.server.infra.youtube;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import site.mutopia.server.infra.youtube.dto.YoutubePlaylistSaveReqDto;

@Component
@Slf4j
@RequiredArgsConstructor
public class YoutubeApi {

    private static final String BASE_URL = "https://youtube.googleapis.com/youtube/v3";

    private final WebClient.Builder webClientBuilder;

    private String apiKey;

    private WebClient webClient;

    @Value("${youtube.api.key}")
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
        this.webClient = WebClient.builder()
                .baseUrl(BASE_URL)
                .defaultHeader("Accept", "application/json")
                .build();
    }

    public JsonNode getFirstVideo(String query) {
        try {
            return webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/search")
                            .queryParam("part", "snippet")
                            .queryParam("maxResults", 1)
                            .queryParam("q", query)
                            .queryParam("key", this.apiKey)
                            .build())
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(), clientResponse -> {
                        return clientResponse.bodyToMono(String.class)
                                .flatMap(errorBody -> {
                                    log.error("API error: {}", errorBody);
                                    return Mono.error(new RuntimeException("API error: " + errorBody));
                                });
                    })
                    .bodyToMono(JsonNode.class)
                    .doOnError(WebClientResponseException.class, ex -> {
                        log.error("WebClientResponseException: Status {}, Message {}", ex.getRawStatusCode(), ex.getMessage());
                    })
                    .doOnError(Exception.class, ex -> {
                        log.error("Exception: {}", ex.getMessage());
                    })
                    .block();
        } catch (WebClientResponseException ex) {
            log.error("Error response: Status {}, Body {}", ex.getRawStatusCode(), ex.getResponseBodyAsString());
            throw ex;
        } catch (Exception ex) {
            log.error("General error: {}", ex.getMessage());
            throw ex;
        }
    }

    public JsonNode getPlaylists(String accessToken) {
        try {
            return webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/playlists")
                            .queryParam("part", "snippet")
                            .queryParam("mine", "true")
                            .queryParam("key", this.apiKey)
                            .build())
                    .header("Authorization", "Bearer " + accessToken)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(), clientResponse -> {
                        return clientResponse.bodyToMono(String.class)
                                .flatMap(errorBody -> {
                                    log.error("API error: {}", errorBody);
                                    return Mono.error(new RuntimeException("API error: " + errorBody));
                                });
                    })
                    .bodyToMono(JsonNode.class)
                    .doOnError(WebClientResponseException.class, ex -> {
                        log.error("WebClientResponseException: Status {}, Message {}", ex.getRawStatusCode(), ex.getMessage());
                    })
                    .doOnError(Exception.class, ex -> {
                        log.error("Exception: {}", ex.getMessage());
                    })
                    .block();
        } catch (WebClientResponseException ex) {
            log.error("Error response: Status {}, Body {}", ex.getRawStatusCode(), ex.getResponseBodyAsString());
            throw ex;
        } catch (Exception ex) {
            log.error("General error: {}", ex.getMessage());
            throw ex;
        }
    }

    public JsonNode savePlaylist(String accessToken, String title, String description) {
        try {
            YoutubePlaylistSaveReqDto request =
                    YoutubePlaylistSaveReqDto.builder()
                            .snippet(YoutubePlaylistSaveReqDto.Snippet.builder()
                                    .title(title)
                                    .description(description)
                                    .build())
                            .status(YoutubePlaylistSaveReqDto.Status.builder().privacyStatus("public").build())
                            .build();

            return webClient.post()
                    .uri(uriBuilder -> uriBuilder
                            .path("/playlists")
                            .queryParam("part", "id")
                            .queryParam("part", "contentDetails")
                            .queryParam("part", "player")
                            .queryParam("part", "snippet")
                            .queryParam("part", "status")
                            .queryParam("key", this.apiKey)
                            .build())
                    .header("Authorization", "Bearer " + accessToken)
                    .header("Content-Type", "application/json")
                    .body(Mono.just(request), YoutubePlaylistSaveReqDto.class)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(), clientResponse -> {
                        return clientResponse.bodyToMono(String.class)
                                .flatMap(errorBody -> {
                                    log.error("API error: {}", errorBody);
                                    return Mono.error(new RuntimeException("API error: " + errorBody));
                                });
                    })
                    .bodyToMono(JsonNode.class)
                    .doOnError(WebClientResponseException.class, ex -> {
                        log.error("WebClientResponseException: Status {}, Message {}", ex.getRawStatusCode(), ex.getMessage());
                    })
                    .doOnError(Exception.class, ex -> {
                        log.error("Exception: {}", ex.getMessage());
                    })
                    .block();
        } catch (WebClientResponseException ex) {
            log.error("Error response: Status {}, Body {}", ex.getRawStatusCode(), ex.getResponseBodyAsString());
            throw ex;
        } catch (Exception ex) {
            log.error("General error: {}", ex.getMessage());
            throw ex;
        }
    }


    public JsonNode addVideoToPlaylist(String accessToken, String playlistId, String videoId) {
        try {
            JsonNode request = createAddVideoRequest(playlistId, videoId);

            return webClient.post()
                    .uri(uriBuilder -> uriBuilder
                            .path("/playlistItems")
                            .queryParam("part", "id")
                            .queryParam("part", "contentDetails")
                            .queryParam("part", "snippet")
                            .queryParam("part", "status")
                            .queryParam("key", this.apiKey)
                            .build())
                    .header("Authorization", "Bearer " + accessToken)
                    .header("Content-Type", "application/json")
                    .body(Mono.just(request), JsonNode.class)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(), clientResponse -> {
                        return clientResponse.bodyToMono(String.class)
                                .flatMap(errorBody -> {
                                    log.error("API error: {}", errorBody);
                                    return Mono.error(new RuntimeException("API error: " + errorBody));
                                });
                    })
                    .bodyToMono(JsonNode.class)
                    .doOnError(WebClientResponseException.class, ex -> {
                        log.error("WebClientResponseException: Status {}, Message {}", ex.getRawStatusCode(), ex.getMessage());
                    })
                    .doOnError(Exception.class, ex -> {
                        log.error("Exception: {}", ex.getMessage());
                    })
                    .block();
        } catch (WebClientResponseException ex) {
            log.error("Error response: Status {}, Body {}", ex.getRawStatusCode(), ex.getResponseBodyAsString());
            throw ex;
        } catch (Exception ex) {
            log.error("General error: {}", ex.getMessage());
            throw ex;
        }
    }

    private JsonNode createAddVideoRequest(String playlistId, String videoId) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode snippetNode = mapper.createObjectNode();
        snippetNode.put("playlistId", playlistId);

        ObjectNode resourceIdNode = mapper.createObjectNode();
        resourceIdNode.put("kind", "youtube#video");
        resourceIdNode.put("videoId", videoId);

        snippetNode.set("resourceId", resourceIdNode);

        ObjectNode requestNode = mapper.createObjectNode();
        requestNode.set("snippet", snippetNode);

        return requestNode;
    }
}
