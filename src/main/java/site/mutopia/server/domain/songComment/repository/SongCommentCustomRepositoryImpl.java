package site.mutopia.server.domain.songComment.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import site.mutopia.server.domain.songComment.dto.SongCommentInfoResDto;
import site.mutopia.server.domain.songComment.dto.OrderBy;

import java.util.List;

@RequiredArgsConstructor
public class SongCommentCustomRepositoryImpl implements SongCommentCustomRepository {

    private final EntityManager em;

    @Override
    public List<SongCommentInfoResDto> findSongCommentsBySongId(String songId, Pageable page, String loginUserId, OrderBy orderBy) {

        String jpql = "select new site.mutopia.server.domain.songComment.dto.SongCommentInfoResDto(" +
                "sc.writer.id, sc.writer.username , sc.writer.profile.profilePicUrl, sc.song.id, sc.song.title, sc.rating, sc.comment, sc.createdAt, " +
                (loginUserId != null ? "exists(select 1 from SongCommentLikeEntity scl where scl.id.songCommentId = sc.id and scl.id.likeUserId = :loginUserId), " : "false, ") +
                "(select count(scl2) from SongCommentLikeEntity scl2 where scl2.id.songCommentId = sc.id), " +
                "sc.song.album.id, sc.song.album.name, sc.song.album.artistName, sc.song.album.coverImageUrl " +
                ") from SongCommentEntity sc " +
                "where sc.song.id = :songId " +
                (orderBy == OrderBy.RECENT ? "order by sc.createdAt desc" : "order by (select count(scl3) from SongCommentLikeEntity scl3 where scl3.id.songCommentId = sc.id) desc");

        TypedQuery<SongCommentInfoResDto> query = em.createQuery(jpql, SongCommentInfoResDto.class)
                .setParameter("songId", songId);

        if (loginUserId != null) {
            query.setParameter("loginUserId", loginUserId);
        }

        return query.setFirstResult((int) page.getOffset())
                .setMaxResults(page.getPageSize())
                .getResultList();
    }

    @Override
    public List<SongCommentInfoResDto> findCommentsByUserId(String userId, Pageable pageable, String loginUserId) {
        String jpql = "select new site.mutopia.server.domain.songComment.dto.SongCommentInfoResDto(" +
                "sc.writer.id, sc.writer.username , sc.writer.profile.profilePicUrl, sc.song.id, sc.song.title, sc.rating, sc.comment, sc.createdAt, " +
                (loginUserId != null ? "exists(select 1 from SongCommentLikeEntity scl where scl.id.songCommentId = sc.id and scl.id.likeUserId = :loginUserId), " : "false, ") +
                "(select count(scl2) from SongCommentLikeEntity scl2 where scl2.songComment.song.id = sc.song.id), " +
                "sc.song.album.id, sc.song.album.name, sc.song.album.artistName, sc.song.album.coverImageUrl " +
                ") from SongCommentEntity sc " +
                "where sc.writer.id = :userId " +
                "order by sc.createdAt desc";

        TypedQuery<SongCommentInfoResDto> query = em.createQuery(jpql, SongCommentInfoResDto.class)
                .setParameter("userId", userId);

        if (loginUserId != null) {
            query.setParameter("loginUserId", loginUserId);
        }

        return query.setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
    }

    @Override
    public List<SongCommentInfoResDto> findCommentsOrderByCreatedAtDesc(Pageable pageable, String loginUserId) {
        String jpql = "select new site.mutopia.server.domain.songComment.dto.SongCommentInfoResDto(" +
                "sc.writer.id, sc.writer.username , sc.writer.profile.profilePicUrl, sc.song.id, sc.song.title, sc.rating, sc.comment, sc.createdAt, " +
                (loginUserId != null ? "exists(select 1 from SongCommentLikeEntity scl where scl.id.songCommentId = sc.id and scl.id.likeUserId = :loginUserId), " : "false, ") +
                "(select count(scl2) from SongCommentLikeEntity scl2 where scl2.songComment.song.id = sc.song.id), " +
                "sc.song.album.id, sc.song.album.name, sc.song.album.artistName, sc.song.album.coverImageUrl " +
                ") from SongCommentEntity sc " +
                "order by sc.createdAt desc";

        TypedQuery<SongCommentInfoResDto> query = em.createQuery(jpql, SongCommentInfoResDto.class);

        if (loginUserId != null) {
            query.setParameter("loginUserId", loginUserId);
        }

        return query.setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
    }

    @Override
    public List<SongCommentInfoResDto> findCommentsByAlbumId(String albumId, Pageable pageable, String loginUserId, OrderBy orderBy) {

        String jpql = "select new site.mutopia.server.domain.songComment.dto.SongCommentInfoResDto(" +
                "sc.writer.id, sc.writer.username , sc.writer.profile.profilePicUrl, sc.song.id, sc.song.title, sc.rating, sc.comment, sc.createdAt, " +
                (loginUserId != null ? "exists(select 1 from SongCommentLikeEntity scl where scl.id.songCommentId = sc.id and scl.id.likeUserId = :loginUserId), " : "false, ") +
                "(select count(scl2) from SongCommentLikeEntity scl2 where scl2.id.songCommentId = sc.id), " +
                "sc.song.album.id, sc.song.album.name, sc.song.album.artistName, sc.song.album.coverImageUrl " +
                ") from SongCommentEntity sc " +
                "where sc.song.album.id = :albumId " +
                (orderBy == OrderBy.RECENT ? "order by sc.createdAt desc" : "order by (select count(scl3) from SongCommentLikeEntity scl3 where scl3.id.songCommentId = sc.id) desc");

        TypedQuery<SongCommentInfoResDto> query = em.createQuery(jpql, SongCommentInfoResDto.class)
                .setParameter("albumId", albumId);

        if (loginUserId != null) {
            query.setParameter("loginUserId", loginUserId);
        }

        return query.setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
    }

    @Override
    public SongCommentInfoResDto findSongCommentByUserIdAndSongId(String userId, String songId, String loginUserId) {
        String jpql = "select new site.mutopia.server.domain.songComment.dto.SongCommentInfoResDto(" +
                "sc.writer.id, sc.writer.username , sc.writer.profile.profilePicUrl, sc.song.id, sc.song.title, sc.rating, sc.comment, sc.createdAt, " +
                (loginUserId != null ? "exists(select 1 from SongCommentLikeEntity scl where scl.id.songCommentId = sc.id and scl.id.likeUserId = :loginUserId), " : "false, ") +
                "(select count(scl2) from SongCommentLikeEntity scl2 where scl2.songComment.song.id = sc.song.id), " +
                "sc.song.album.id, sc.song.album.name, sc.song.album.artistName, sc.song.album.coverImageUrl " +
                ") from SongCommentEntity sc " +
                "where sc.song.id = :songId " +
                "and sc.writer.id = :userId " +
                "order by sc.createdAt desc";

        TypedQuery<SongCommentInfoResDto> query = em.createQuery(jpql, SongCommentInfoResDto.class)
                .setParameter("userId", userId)
                .setParameter("songId", songId);

        if (loginUserId != null) {
            query.setParameter("loginUserId", loginUserId);
        }

        return query.getSingleResult();
    }
}
