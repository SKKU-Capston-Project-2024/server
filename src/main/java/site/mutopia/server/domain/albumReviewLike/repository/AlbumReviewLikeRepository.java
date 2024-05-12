package site.mutopia.server.domain.albumReviewLike.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.mutopia.server.domain.albumReviewLike.entity.AlbumReviewLikeEntity;
import site.mutopia.server.domain.albumReviewLike.entity.AlbumReviewLikeId;

public interface AlbumReviewLikeRepository extends JpaRepository<AlbumReviewLikeEntity, AlbumReviewLikeId> {
}
