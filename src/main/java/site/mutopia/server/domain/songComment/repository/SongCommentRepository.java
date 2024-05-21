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

    @Query("SELECT new site.mutopia.server.domain.songComment.dto.SongCommentInfoResDto(" +
            "new site.mutopia.server.domain.songComment.dto.SongCommentInfoResDto$CommentWriterInfo(writer.id, writer.username, writer.profile.profilePicUrl), " +
            "new site.mutopia.server.domain.songComment.dto.SongCommentInfoResDto$SongCommentInfo(" +
            "new site.mutopia.server.domain.songComment.dto.SongCommentInfoResDto$SongInfo(song.id, song.title, song.duration, song.releaseDate), " +
            "comment.rating, comment.comment)) " +
            "FROM SongCommentEntity comment " +
            "JOIN comment.writer writer " +
            "JOIN comment.song song " +
            "WHERE writer.id = :userId")
    Page<SongCommentInfoResDto> findCommentsByUserId(@Param("userId") String userId, Pageable pageable);

    @Query("SELECT new site.mutopia.server.domain.songComment.dto.SongCommentInfoResDto(" +
            "new site.mutopia.server.domain.songComment.dto.SongCommentInfoResDto$CommentWriterInfo(writer.id, writer.username, writer.profile.profilePicUrl), " +
            "new site.mutopia.server.domain.songComment.dto.SongCommentInfoResDto$SongCommentInfo(" +
            "new site.mutopia.server.domain.songComment.dto.SongCommentInfoResDto$SongInfo(song.id, song.title, song.duration, song.releaseDate), " +
            "comment.rating, comment.comment)) " +
            "FROM SongCommentEntity comment " +
            "JOIN comment.writer writer " +
            "JOIN comment.song song " +
            "ORDER BY comment.createdAt DESC")
    Page<SongCommentInfoResDto> findCommentsOrderByCreatedAtDesc(Pageable pageable);



    @Query("SELECT new site.mutopia.server.domain.songComment.dto.SongCommentInfoResDto(" +
            "new site.mutopia.server.domain.songComment.dto.SongCommentInfoResDto$CommentWriterInfo(writer.id, writer.username, writer.profile.profilePicUrl), " +
            "new site.mutopia.server.domain.songComment.dto.SongCommentInfoResDto$SongCommentInfo(" +
            "new site.mutopia.server.domain.songComment.dto.SongCommentInfoResDto$SongInfo(song.id, song.title, song.duration, song.releaseDate), " +
            "comment.rating, comment.comment)) " +
            "FROM SongCommentEntity comment " +
            "JOIN comment.writer writer " +
            "JOIN comment.song song " +
            "JOIN song.album album " +
            "WHERE album.id = :albumId " +
            "ORDER BY comment.createdAt DESC")
    Page<SongCommentInfoResDto> findCommentsByAlbumIdOrderByCreatedAtDesc(@Param("albumId") String albumId, Pageable pageable);


    @Query("SELECT COUNT(sc) FROM SongCommentEntity sc WHERE sc.writer.id = :writerId")
    Long countByWriterId(@Param("writerId") String writerId);

}
