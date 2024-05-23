package site.mutopia.server.domain.songComment.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import site.mutopia.server.domain.songComment.dto.SongCommentInfoResDto;
import site.mutopia.server.domain.songComment.entity.SongCommentEntity;
import site.mutopia.server.domain.songComment.entity.SongCommentId;

@Repository
public interface SongCommentRepository extends JpaRepository<SongCommentEntity, SongCommentId> {

    @Query("SELECT distinct c FROM SongCommentEntity c left join fetch c.song left join fetch c.writer left join fetch c.song.album left join fetch c.writer.profile where c.writer.id = :userId order by c.createdAt desc")
    Page<SongCommentEntity> findCommentsByUserId(@Param("userId") String userId, Pageable pageable);

    @Query("SELECT distinct c FROM SongCommentEntity c left join fetch c.song left join fetch c.writer left join fetch c.song.album left join fetch c.writer.profile order by c.createdAt desc")
    Page<SongCommentEntity> findCommentsOrderByCreatedAtDesc(Pageable pageable);

    @Query("SELECT distinct c FROM SongCommentEntity c left join fetch c.song left join fetch c.writer left join fetch c.song.album left join fetch c.writer.profile where c.song.id = :songId order by c.createdAt desc")
    Page<SongCommentEntity> findCommentsOrderByCreatedAtDesc(@Param("songId") String songId, Pageable pageable);



    @Query("SELECT distinct c FROM SongCommentEntity c left join fetch c.song left join fetch c.writer left join fetch c.song.album left join fetch c.writer.profile where c.song.album.id = :albumId order by c.createdAt desc")
    Page<SongCommentEntity> findCommentsByAlbumIdOrderByCreatedAtDesc(@Param("albumId") String albumId, Pageable pageable);


    @Query("SELECT COUNT(sc) FROM SongCommentEntity sc WHERE sc.writer.id = :writerId")
    Long countByWriterId(@Param("writerId") String writerId);
}
