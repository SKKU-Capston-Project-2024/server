package site.mutopia.server.domain.albumReviewLike.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import site.mutopia.server.domain.albumReviewLike.entity.AlbumReviewLikeEntity;
import site.mutopia.server.domain.albumReviewLike.entity.AlbumReviewLikeId;

public interface AlbumReviewLikeRepository extends JpaRepository<AlbumReviewLikeEntity, AlbumReviewLikeId>, AlbumReviewLikeCustomRepository {
    @Query("select count(reviewLike) from AlbumReviewLikeEntity reviewLike where reviewLike.review.id = :albumReviewId")
    Long countLikesByAlbumReviewId(@Param("albumReviewId") Long albumReviewId);

    Boolean existsByReview_IdAndUser_Id(Long reviewId, String userId);

    @Modifying
    @Query("delete from AlbumReviewLikeEntity reviewLike where reviewLike.review.id = :albumReviewId")
    void deleteByAlbumReviewId(@Param("albumReviewId") Long albumReviewId);
}
