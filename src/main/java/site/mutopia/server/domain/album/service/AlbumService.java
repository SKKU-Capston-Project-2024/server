package site.mutopia.server.domain.album.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import site.mutopia.server.domain.album.dto.response.AlbumDetailResDto;
import site.mutopia.server.domain.album.entity.AlbumEntity;
import site.mutopia.server.domain.album.exception.AlbumNotFoundException;
import site.mutopia.server.domain.album.repository.AlbumRepository;
import site.mutopia.server.domain.albumReview.dto.AlbumReviewInfoDto;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlbumService {

    private final AlbumRepository albumRepository;

    public AlbumDetailResDto findAlbumById(String albumId) {
        AlbumEntity albumEntity = albumRepository.findAlbumById(albumId).orElseThrow(() -> new AlbumNotFoundException("Album not found. albumId: " + albumId + " does not exist."));

        return AlbumDetailResDto.toDto(albumEntity);
    }

    public void findAlbumByAlbumName(String albumName) {

    }

    public List<AlbumEntity> searchAlbumByKeyword(String keyword ,int offset) {
        List<AlbumEntity> albumByKeyword = albumRepository.findAlbumByKeyword(keyword, 10, offset);
        return albumByKeyword;
    }

}
