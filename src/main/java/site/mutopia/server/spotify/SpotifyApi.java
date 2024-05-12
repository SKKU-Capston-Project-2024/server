package site.mutopia.server.spotify;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import site.mutopia.server.spotify.dto.PagedTracks;
import site.mutopia.server.spotify.dto.SearchAlbumsDto;
import site.mutopia.server.spotify.dto.item.Albums;
import site.mutopia.server.spotify.dto.track.SearchTracksDto;
import site.mutopia.server.spotify.dto.track.Tracks;

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

    public Albums searchAlbums(String query, int limit, int offset) {
        SearchAlbumsDto searchAlbumsDto = client.get()
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
        if (searchAlbumsDto == null) {
            throw new RuntimeException("Failed to search albums");
        }
        return searchAlbumsDto.getAlbums();
    }

    public Tracks searchTracks(String query, int limit, int offset) {
        SearchTracksDto searchTracksDto = client.get()
                .uri(uriBuilder -> uriBuilder.path("/search")
                        .queryParam("q", query)
                        .queryParam("type", "track")
                        .queryParam("limit", limit)
                        .queryParam("offset", offset)
                        .queryParam("market", "KR")
                        .queryParam("locale", "ko_KR")
                        .build())
                .retrieve()
                .bodyToMono(SearchTracksDto.class)
                .block();
        if (searchTracksDto == null) {
            throw new RuntimeException("Failed to search tracks");
        }
        return searchTracksDto.tracks;
    }

    public PagedTracks getAlbumTracks(String albumId) {
        return client.get()
                .uri(uriBuilder -> uriBuilder.path("/albums/{id}/tracks")
                        .queryParam("market", "KR")
                        .queryParam("limit", 30)
                        .queryParam("offset", 0)
                        .queryParam("locale", "ko_KR")
                        .build(albumId))
                .retrieve()
                .bodyToMono(PagedTracks.class)
                .block();
    }




}