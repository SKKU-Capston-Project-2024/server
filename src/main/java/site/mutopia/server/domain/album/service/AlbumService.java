package site.mutopia.server.domain.album.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import site.mutopia.server.domain.album.dto.response.AlbumDetailResDto;
import site.mutopia.server.domain.album.entity.AlbumEntity;
import site.mutopia.server.domain.album.repository.AlbumRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlbumService {

    private final AlbumRepository albumRepository;

    public AlbumDetailResDto findAlbumById(String albumId) {
        AlbumEntity albumEntity = albumRepository.findAlbumById(albumId);

        return AlbumDetailResDto.toDto(albumEntity);
    }

    public void findAlbumByAlbumName(String albumName) {

    }

    public List<AlbumEntity> searchAlbumByKeyword(String keyword) {
        List<AlbumEntity> albumByKeyword = albumRepository.findAlbumByKeyword(keyword, 10, 0);
        return albumByKeyword;
    }

}
