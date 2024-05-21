package site.mutopia.server.domain.albumReview.repository;

import site.mutopia.server.domain.albumReview.dto.AlbumReviewInfoDto;

import java.util.List;
import java.util.Optional;

public interface AlbumReviewCustomRepository {

    Optional<AlbumReviewInfoDto> findAlbumReviewInfoDto(Long albumReviewId, String loginUserId);

    List<AlbumReviewInfoDto> findAllByUserIdOrderByCreatedAt(String userId, Integer offset, String loginUserId);

    List<AlbumReviewInfoDto> findAllByUserIdOrderByLike(String userId, Integer offset, String loginUserId);

    List<AlbumReviewInfoDto> findAllByAlbumIdOrderByCreatedAt(String albumId, Integer offset, String loginUserId);

    List<AlbumReviewInfoDto> findAllByAlbumIdOrderByLike(String loginUserId, String albumId, Integer offset);

    List<AlbumReviewInfoDto> findAllOrderByCreatedAtDesc(Integer offset, String loginUserId);

    List<AlbumReviewInfoDto> findAllOrderByLikeDesc(String loginUserId, Integer offset);


}
