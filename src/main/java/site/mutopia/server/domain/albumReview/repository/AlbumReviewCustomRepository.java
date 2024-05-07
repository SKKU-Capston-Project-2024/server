package site.mutopia.server.domain.albumReview.repository;

import site.mutopia.server.domain.albumReview.dto.AlbumReviewInfoDto;

import java.util.Optional;

public interface AlbumReviewCustomRepository {

    Optional<AlbumReviewInfoDto> findAlbumReviewInfoDto(Long albumReviewId);
}
