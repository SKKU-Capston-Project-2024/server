package site.mutopia.server.album.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import site.mutopia.server.album.domain.MutopiaAlbum;
import site.mutopia.server.album.repository.AlbumRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlbumService {

    private final AlbumRepository albumRepository;

    public void findAlbumById(String albumId) {
        MutopiaAlbum albumById = albumRepository.findAlbumById(albumId);
        log.info("albumById: {}", albumById);
    }

}
