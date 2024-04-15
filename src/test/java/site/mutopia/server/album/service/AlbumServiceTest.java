package site.mutopia.server.album.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AlbumServiceTest {

    @Autowired
    AlbumService albumService;

    @Test
    void findAlbum() {
        albumService.findAlbumByAlbumName("르세라핌");

    }

}