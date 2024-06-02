package site.mutopia.server.domain.songComment.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import site.mutopia.server.domain.songComment.dto.SongCommentInfoResDto;
import site.mutopia.server.domain.songComment.dto.OrderBy;

import java.util.List;

@Repository
public interface SongCommentCustomRepository {
    List<SongCommentInfoResDto> findSongCommentsBySongId(String songId, Pageable page, String loginUserId, OrderBy orderBy);
    List<SongCommentInfoResDto> findCommentsByUserId(String userId, Pageable pageable, String loginUserId, OrderBy orderBy);
    List<SongCommentInfoResDto> findCommentsOrderByCreatedAtDesc(Pageable pageable, String loginUserId);
    List<SongCommentInfoResDto> findCommentsByAlbumId(String albumId, Pageable pageable, String loginUserId, OrderBy orderBy);
    SongCommentInfoResDto findSongCommentByUserIdAndSongId(String userId, String songId, String loginUserId);

}
