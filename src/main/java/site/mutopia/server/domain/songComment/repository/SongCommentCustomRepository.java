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

import java.util.List;

@Repository
public interface SongCommentCustomRepository {
    List<SongCommentInfoResDto> findSongCommentsBySongId(String songId, Pageable page, String loginUserId);
    List<SongCommentInfoResDto> findCommentsByUserId(String userId, Pageable pageable, String loginUserId);
    List<SongCommentInfoResDto> findCommentsOrderByCreatedAtDesc(Pageable pageable, String loginUserId);
    List<SongCommentInfoResDto> findCommentsByAlbumIdOrderByCreatedAtDesc(String albumId, Pageable pageable, String loginUserId);
    SongCommentInfoResDto findSongCommentByUserIdAndSongId(String userId, String songId, String loginUserId);

}
