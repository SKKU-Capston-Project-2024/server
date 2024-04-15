package site.mutopia.server.spotify;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import se.michaelthelin.spotify.SpotifyApi;
import site.mutopia.server.domain.album.domain.MutopiaAlbum;

import java.util.List;

@Slf4j
@SpringBootTest
class SpotifyAlbumRepositoryTest {

    SpotifyAlbumRepository spotifyAlbumRepository;

    @Autowired
    SpotifyAlbumRepositoryTest(SpotifyApi spotifyApi) {
        this.spotifyAlbumRepository = new SpotifyAlbumRepository(spotifyApi);
    }

    @Test
    void findAlbumById() {
        spotifyAlbumRepository.findAlbumById("4aawyAB9vmqN3uQ7FjRGT");
    }

    @Test
    void findAlbumsByArtist() {
        List<MutopiaAlbum> albums = spotifyAlbumRepository.findAlbumByKeyword("르세라핌", 0, 0);
        log.info("{}", albums.stream().map(MutopiaAlbum::getName).toList());
        Assertions.assertThat(albums).isNotNull();
    }

}