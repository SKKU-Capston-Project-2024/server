package site.mutopia.server.domain.albumReview.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.mutopia.server.domain.albumReview.entity.AlbumReviewEntity;

@Repository
public interface AlbumReviewRepository extends JpaRepository<AlbumReviewEntity, Long>, AlbumReviewCustomRepository {
}