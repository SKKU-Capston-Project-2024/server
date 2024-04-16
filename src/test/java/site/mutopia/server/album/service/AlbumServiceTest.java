package site.mutopia.server.album.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import site.mutopia.server.domain.album.service.AlbumService;

@SpringBootTest
class AlbumServiceTest {

    @Autowired
    AlbumService albumService;

    @Test
    void findAlbum() {
        albumService.findAlbumByAlbumName("르세라핌");

    }

}