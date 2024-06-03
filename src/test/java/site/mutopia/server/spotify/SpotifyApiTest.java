package site.mutopia.server.spotify;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import site.mutopia.server.spotify.dto.PagedTracks;
import site.mutopia.server.spotify.dto.SearchAlbumsDto;
import site.mutopia.server.spotify.dto.item.Albums;
import site.mutopia.server.spotify.dto.track.Tracks;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class SpotifyApiTest {

    @Autowired
    private SpotifyApi spotifyApi;

    @Autowired
    private SpotifyClientManager manager;

    @Test
    void getSpotifyApi() {
        assertNotNull(manager);
    }

    @Test
    void searchAlbums() {
        Albums bts = spotifyApi.searchAlbums("적재", 10, 0);
        assertNotNull(bts);
    }

    @Test
    void getAlbumTracks() {
        PagedTracks albumTracks = spotifyApi.getAlbumTracks("7z61DsZtWO2S4nC5xd0b9p");
        log.info("albumTracks: {}", albumTracks);
        assertNotNull(albumTracks);
    }

    @Test
    void searchTracks() {
        Tracks tracks = spotifyApi.searchTracks("강남스타일", 10, 0);
        log.info("tracks: {}", tracks.items.stream().map(track -> track.name).toArray());
        assertNotNull(tracks);
    }

    @Test
    void getTrackInfo() {
        var trackInfo = spotifyApi.getTrackInfo("6bvZRLLkBKkmgpBJTTj3QK");
        log.info("trackInfo: {}", trackInfo);
        assertNotNull(trackInfo);
    }

}