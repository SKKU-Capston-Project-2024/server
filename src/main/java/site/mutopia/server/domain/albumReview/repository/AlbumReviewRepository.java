package site.mutopia.server.domain.albumReview.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import site.mutopia.server.domain.albumReview.entity.AlbumReviewEntity;

import java.util.Optional;

@Repository
public interface AlbumReviewRepository extends JpaRepository<AlbumReviewEntity, Long>, AlbumReviewCustomRepository {


    @Query("SELECT a FROM AlbumReviewEntity a WHERE a.id = :albumId AND a.writer = :writer")
    Optional<AlbumReviewEntity> findByAlbumIdAndUserId(String albumId, String writer);

    Long countByWriterId(String writerId);

}