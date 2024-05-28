package site.mutopia.server.domain.albumReview.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import site.mutopia.server.domain.albumReview.entity.AlbumReviewEntity;

import java.util.Optional;

@Repository
public interface AlbumReviewRepository extends JpaRepository<AlbumReviewEntity, Long>, AlbumReviewCustomRepository {

    @Query("SELECT a FROM AlbumReviewEntity a WHERE a.id = :albumId AND a.writer = :writer")
    Optional<AlbumReviewEntity> findByAlbumIdAndUserId(@Param("albumId") String albumId, @Param("writer") String writerId);

    Long countByWriterId(String writerId);

    Optional<AlbumReviewEntity> findByWriterId(String writerId);

    Optional<AlbumReviewEntity> findByWriterIdAndAlbumId(String writerId, String albumId);

    @Query("SELECT a FROM AlbumReviewEntity a WHERE a.writer.id = :userId AND a.pinned = true")
    Optional<AlbumReviewEntity> findByUserIdAndPinned(@Param("userId") String userId);

    @Modifying
    @Transactional
    @Query("UPDATE AlbumReviewEntity a SET a.pinned = false WHERE a.writer.id = :userId AND a.pinned = true")
    void updatePinnedToFalseByUserId(@Param("userId") String userId);
}