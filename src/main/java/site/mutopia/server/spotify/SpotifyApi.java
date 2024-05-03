package site.mutopia.server.spotify;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import site.mutopia.server.spotify.dto.SearchAlbumsDto;

@Slf4j
@Component
@RequiredArgsConstructor
public class SpotifyApi {

    private final SpotifyClientManager manager;

    private WebClient client;

    @PostConstruct
    public void init() {
        client = manager.getSpotifyClient();
    }

    public SearchAlbumsDto searchAlbums(String query, int limit,int offset) {
        return client.get()
                .uri(uriBuilder -> uriBuilder.path("/search")
                        .queryParam("q", query)
                        .queryParam("type", "album")
                        .queryParam("limit", limit)
                        .queryParam("offset", offset)
                        .queryParam("market", "KR")
                        .queryParam("locale", "ko_KR")
                        .build())
                .retrieve()
                .bodyToMono(SearchAlbumsDto.class)
                .block();
    }
}