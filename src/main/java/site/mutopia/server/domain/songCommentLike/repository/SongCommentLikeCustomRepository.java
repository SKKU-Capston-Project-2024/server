package site.mutopia.server.domain.songCommentLike.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import site.mutopia.server.domain.songComment.dto.SongCommentInfoResDto;
import site.mutopia.server.domain.songCommentLike.dto.LikeSongCommentDto;
import site.mutopia.server.domain.songCommentLike.entity.SongCommentLikeEntity;

import java.util.List;

public interface SongCommentLikeCustomRepository {
    List<SongCommentInfoResDto> findLikedSongsByUserId(String userId, Pageable page, String loginUserId);
}
