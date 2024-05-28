package site.mutopia.server.domain.songComment.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import site.mutopia.server.domain.songComment.dto.SongCommentInfoResDto;
import site.mutopia.server.domain.songComment.entity.SongCommentEntity;

import java.util.List;

@RequiredArgsConstructor
public class SongCommentCustomRepositoryImpl implements SongCommentCustomRepository {

    private final EntityManager em;


    @Override
    public List<SongCommentInfoResDto> findSongCommentsBySongId(String songId, Pageable page, String loginUserId) {

        String jpql = "select new site.mutopia.server.domain.songComment.dto.SongCommentInfoResDto(" +
                "sc.writer.id, sc.writer.username , sc.writer.profile.profilePicUrl, sc.song.id, sc.song.title, sc.rating, sc.comment, sc.createdAt, " +
                (loginUserId != null ? "exists(select 1 from SongCommentLikeEntity scl where scl.id.songCommentId = sc.id and scl.id.likeUserId = :loginUserId) " : "false ") +
                ") from SongCommentEntity sc " +
                "where sc.song.id = :songId " +
                "order by sc.createdAt desc";

        TypedQuery<SongCommentInfoResDto> query = em.createQuery(jpql, SongCommentInfoResDto.class)
                .setParameter("songId", songId);

        if (loginUserId != null) {
            query.setParameter("loginUserId", loginUserId);
        }

        return query.setFirstResult((int) page.getOffset())
                .setMaxResults(page.getPageSize())
                .setFirstResult((int) page.getOffset())
                .getResultList();
    }

    @Override
    public List<SongCommentInfoResDto> findCommentsByUserId(String userId, Pageable pageable, String loginUserId) {
        String jpql = "select new site.mutopia.server.domain.songComment.dto.SongCommentInfoResDto(" +
                "sc.writer.id, sc.writer.username , sc.writer.profile.profilePicUrl, sc.song.id, sc.song.title, sc.rating, sc.comment, sc.createdAt, " +
                (loginUserId != null ? "exists(select 1 from SongCommentLikeEntity scl where scl.id.songCommentId = sc.id and scl.id.likeUserId = :loginUserId) " : "false ") +
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
                (loginUserId != null ? "exists(select 1 from SongCommentLikeEntity scl where scl.id.songCommentId = sc.id and scl.id.likeUserId = :loginUserId) " : "false ") +
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
    public List<SongCommentInfoResDto> findCommentsByAlbumIdOrderByCreatedAtDesc(String albumId, Pageable pageable, String loginUserId) {
        String jpql = "select new site.mutopia.server.domain.songComment.dto.SongCommentInfoResDto(" +
                "sc.writer.id, sc.writer.username , sc.writer.profile.profilePicUrl, sc.song.id, sc.song.title, sc.rating, sc.comment, sc.createdAt, " +
                (loginUserId != null ? "exists(select 1 from SongCommentLikeEntity scl where scl.id.songCommentId = sc.id and scl.id.likeUserId = :loginUserId) " : "false ") +
                ") from SongCommentEntity sc " +
                "where sc.song.album.id = :albumId " +
                "order by sc.createdAt desc";


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
                (loginUserId != null ? "exists(select 1 from SongCommentLikeEntity scl where scl.id.songCommentId = sc.id and scl.id.likeUserId = :loginUserId) " : "false ") +
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
