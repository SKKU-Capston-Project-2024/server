package site.mutopia.server.domain.album.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import site.mutopia.server.domain.album.domain.MutopiaAlbum;
import site.mutopia.server.domain.album.repository.AlbumRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlbumService {

    private final AlbumRepository albumRepository;

    public void findAlbumById(String albumId) {
        MutopiaAlbum albumById = albumRepository.findAlbumById(albumId);
        log.info("albumById: {}", albumById);
    }

    public void findAlbumByAlbumName(String albumName) {
        List<MutopiaAlbum> albumByAlbumName = albumRepository.findAlbumByKeyword(albumName, 0, 0);
        log.info("albumByAlbumName: {}", albumByAlbumName.stream().map(MutopiaAlbum::getName).toList());
    }
}
