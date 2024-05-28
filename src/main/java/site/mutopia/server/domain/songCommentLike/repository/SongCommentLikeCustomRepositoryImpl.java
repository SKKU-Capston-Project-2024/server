package site.mutopia.server.domain.songCommentLike.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import site.mutopia.server.domain.songCommentLike.dto.LikeSongCommentDto;
import site.mutopia.server.domain.songCommentLike.entity.SongCommentLikeEntity;

import java.util.List;

@RequiredArgsConstructor
public class SongCommentLikeCustomRepositoryImpl implements SongCommentLikeCustomRepository{

    private final EntityManager em;

    @Override
    public List<LikeSongCommentDto> findLikedSongsByUserId(String userId, Pageable page, String loginUserId) {
        String jpql = "select new site.mutopia.server.domain.songCommentLike.dto.LikeSongCommentDto(" +
                "scl.songComment.song.id, scl.songComment.song.album.id, scl.songComment.song.album.coverImageUrl, scl.songComment.song.title, scl.songComment.song.album.artistName, scl.songComment.song.album.name, scl.songComment.writer.id, scl.songComment.writer.username, scl.songComment.writer.profile.profilePicUrl, scl.songComment.comment, " +
                (loginUserId != null ? "exists(select 1 from SongCommentLikeEntity scl2 where scl2.songComment.id = scl.songComment.id and scl2.likeUser.id = :loginUserId) " : "false ") +
                ") from SongCommentLikeEntity scl " +
                "where scl.likeUser.id = :userId " +
                "order by scl.songComment.createdAt desc";

        TypedQuery<LikeSongCommentDto> query = em.createQuery(jpql, LikeSongCommentDto.class)
                .setParameter("userId", userId);
        if (loginUserId != null) {
            query.setParameter("loginUserId", loginUserId);
        }
        return query.setFirstResult((int) page.getOffset())
                .setMaxResults(page.getPageSize())
                .getResultList();
    }
}
