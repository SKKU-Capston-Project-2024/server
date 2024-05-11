package site.mutopia.server.domain.albumReview.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.mutopia.server.domain.albumReview.entity.AlbumReviewEntity;

import java.util.Optional;

@Repository
public interface AlbumReviewRepository extends JpaRepository<AlbumReviewEntity, Long>, AlbumReviewCustomRepository {
    Long countByWriterId(String writerId);

    Optional<AlbumReviewEntity> findByWriterIdAndAlbumId(String writerId, String albumId);
}