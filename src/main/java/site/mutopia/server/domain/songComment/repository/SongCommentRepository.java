package site.mutopia.server.domain.songComment.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import site.mutopia.server.domain.songComment.dto.SongCommentInfoResDto;
import site.mutopia.server.domain.songComment.entity.SongCommentEntity;
import site.mutopia.server.domain.songComment.entity.SongCommentId;

@Repository
public interface SongCommentRepository extends JpaRepository<SongCommentEntity, SongCommentId>, SongCommentCustomRepository {

    @Query("SELECT COUNT(sc) FROM SongCommentEntity sc WHERE sc.writer.id = :writerId")
    Long countByWriterId(@Param("writerId") String writerId);

    @Modifying
    @Query("delete from SongCommentEntity sc where sc.song.id = :songId and sc.writer.id = :writerId")
    void deleteBySongId(@Param("songId") String songId,@Param("writerId") String writerId);
}
