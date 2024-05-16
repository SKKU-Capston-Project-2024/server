package site.mutopia.server.domain.albumReview.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import site.mutopia.server.domain.albumReview.entity.AlbumReviewEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlbumReviewRepository extends JpaRepository<AlbumReviewEntity, Long>, AlbumReviewCustomRepository {


    @Query("SELECT a FROM AlbumReviewEntity a WHERE a.id = :albumId AND a.writer = :writer")
    Optional<AlbumReviewEntity> findByAlbumIdAndUserId(@Param("albumId") String albumId, @Param("writer") String writerId);

    Long countByWriterId(String writerId);

    Optional<AlbumReviewEntity> findByWriterId(String writerId);

    Optional<AlbumReviewEntity> findByWriterIdAndAlbumId(String writerId, String albumId);

    //find recent review by albumId desc
    @Query("SELECT a FROM AlbumReviewEntity a WHERE a.album.id = :albumId ORDER BY a.createdAt DESC limit :limit offset :offset")
    List<AlbumReviewEntity> findRecentReviewByAlbumId(@Param("albumId") String albumId, @Param("limit") int limit, @Param("offset") int offset);

}