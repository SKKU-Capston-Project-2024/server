package site.mutopia.server.spotify;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import site.mutopia.server.spotify.dto.SearchAlbumsDto;

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
        SearchAlbumsDto bts = spotifyApi.searchAlbums("르세라핌", 10, 0);
        log.info("result :{}", bts.albums.items.get(0).name);
        assertNotNull(bts);
    }
}