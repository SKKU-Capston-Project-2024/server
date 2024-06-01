package site.mutopia.server.domain.songComment.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import site.mutopia.server.domain.songComment.dto.SongCommentInfoResDto;
import site.mutopia.server.domain.songComment.dto.SongCommentOrderBy;

import java.util.List;

@Repository
public interface SongCommentCustomRepository {
    List<SongCommentInfoResDto> findSongCommentsBySongId(String songId, Pageable page, String loginUserId, SongCommentOrderBy orderBy);
    List<SongCommentInfoResDto> findCommentsByUserId(String userId, Pageable pageable, String loginUserId);
    List<SongCommentInfoResDto> findCommentsOrderByCreatedAtDesc(Pageable pageable, String loginUserId);
    List<SongCommentInfoResDto> findCommentsByAlbumIdOrderByCreatedAtDesc(String albumId, Pageable pageable, String loginUserId);
    SongCommentInfoResDto findSongCommentByUserIdAndSongId(String userId, String songId, String loginUserId);

}
