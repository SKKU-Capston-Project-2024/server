package site.mutopia.server.domain.albumReview.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import site.mutopia.server.domain.albumReview.dto.AlbumReviewInfoDto;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class AlbumReviewCustomRepositoryImpl implements AlbumReviewCustomRepository {

    private final EntityManager em;


    @Override
    public Optional<AlbumReviewInfoDto> findAlbumReviewInfoDto(Long albumReviewId, String loginUserId) {
        String jpql = "SELECT new site.mutopia.server.domain.albumReview.dto.AlbumReviewInfoDto(" +
                "CAST(review.id AS long), " +
                "review.title, review.content, review.rating, " +
                "CAST((select count(albumReviewLike) from AlbumReviewLikeEntity albumReviewLike where albumReviewLike.review.id = review.id) AS long), " +
                "review.createdAt, album.id, writer.id, writer.username, " +
                "CAST((select profilePicUrl from ProfileEntity profile where profile.user.id = writer.id) AS string), " +
                "album.name, album.artistName, album.coverImageUrl, album.releaseDate, album.length, " +
                "CAST((select count(albumReview1) from AlbumReviewEntity albumReview1 where albumReview1.album.id = album.id) AS long), " +
                "CAST((select count(albumLike) from AlbumLikeEntity albumLike where albumLike.album.id = album.id) AS long), " +
                (loginUserId != null ? "CAST((select count(*)>0 from AlbumReviewLikeEntity albumReviewLike2 where albumReviewLike2.review.id = review.id and albumReviewLike2.user.id = :loginUserId) AS boolean ) " : "FALSE ") +
                ") FROM AlbumReviewEntity review " +
                "INNER JOIN review.album album " +
                "INNER JOIN review.writer writer " +
                "WHERE review.id = :albumReviewId";

        try {
            TypedQuery<AlbumReviewInfoDto> query = em.createQuery(jpql, AlbumReviewInfoDto.class)
                    .setParameter("albumReviewId", albumReviewId);
            if (loginUserId != null) {
                query.setParameter("userId", loginUserId);
            }
            AlbumReviewInfoDto result = query.getSingleResult();
            return Optional.of(result);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<AlbumReviewInfoDto> findAllByUserIdOrderByLike(String userId, Integer offset, String loginUserId) {
        String jpql = "SELECT new site.mutopia.server.domain.albumReview.dto.AlbumReviewInfoDto( " +
                "CAST(review.id AS long), " +
                "review.title, review.content, review.rating, " +
                "CAST((select count(albumReviewLike) from AlbumReviewLikeEntity albumReviewLike where albumReviewLike.review.id = review.id) AS long), " +
                "review.createdAt, " +
                "album.id, writer.id, writer.username, " +
                "CAST((select profilePicUrl from ProfileEntity profile where profile.user.id = writer.id) AS string), " +
                "album.name, album.artistName, album.coverImageUrl, album.releaseDate, " +
                "album.length, " +
                "CAST((select count(albumReview1) from AlbumReviewEntity albumReview1 where albumReview1.album.id = album.id) AS long), " +
                "CAST((select count(albumLike) from AlbumLikeEntity albumLike where albumLike.album.id = album.id) AS long), " +
                (loginUserId != null ? "CAST((select count(*)>0 from AlbumReviewLikeEntity albumReviewLike2 where albumReviewLike2.review.id = review.id and albumReviewLike2.user.id = :loginUserId) AS boolean ) " : "FALSE ") +
                ") FROM AlbumReviewEntity review " +
                "INNER JOIN review.album album " +
                "INNER JOIN review.writer writer " +
                "WHERE writer.id = :userId " +
                "ORDER BY (select count(albumReviewLike) from AlbumReviewLikeEntity albumReviewLike where albumReviewLike.review.id = review.id) DESC";


        TypedQuery<AlbumReviewInfoDto> query = em.createQuery(jpql, AlbumReviewInfoDto.class)
                .setParameter("userId", userId)
                .setMaxResults(20)
                .setFirstResult(offset);
        if (loginUserId != null) {
            query.setParameter("loginUserId", loginUserId);
        }
        return query.getResultList();
    }

    @Override
    public List<AlbumReviewInfoDto> findAllByUserIdOrderByCreatedAt(String userId, Integer offset, String loginUserId) {
        String jpql = "SELECT new site.mutopia.server.domain.albumReview.dto.AlbumReviewInfoDto(" +
                "CAST(review.id AS long)," +
                "review.title, review.content, review.rating, " +
                "CAST((select count(albumReviewLike) from AlbumReviewLikeEntity albumReviewLike where albumReviewLike.review.id = review.id) AS long), " +
                "review.createdAt, " +
                "album.id, writer.id, writer.username, " +
                "CAST((select profilePicUrl from ProfileEntity profile where profile.user.id = writer.id) AS string), " +
                "album.name, album.artistName, album.coverImageUrl, album.releaseDate, " +
                "album.length, " +
                "CAST((select count(albumReview1) from AlbumReviewEntity albumReview1 where albumReview1.album.id = album.id) AS long), " +
                "CAST((select count(albumLike) from AlbumLikeEntity albumLike where albumLike.album.id = album.id) AS long), " +
                (loginUserId != null ? "CAST((select count(*)>0 from AlbumReviewLikeEntity albumReviewLike2 where albumReviewLike2.review.id = review.id and albumReviewLike2.user.id = :loginUserId) AS boolean ) " : "FALSE ") +
                ") FROM AlbumReviewEntity review " +
                "INNER JOIN review.album album " +
                "INNER JOIN review.writer writer " +
                "WHERE writer.id = :userId" +
                " ORDER BY review.createdAt DESC";

        TypedQuery<AlbumReviewInfoDto> query = em.createQuery(jpql, AlbumReviewInfoDto.class)
                .setParameter("userId", userId)
                .setMaxResults(20)
                .setFirstResult(offset);
        if (loginUserId != null) {
            query.setParameter("loginUserId", loginUserId);
        }

        return query.getResultList();
    }

    @Override
    public List<AlbumReviewInfoDto> findAllByAlbumIdOrderByCreatedAt(String albumId, Integer offset, String loginUserId) {
        String jpql = "SELECT new site.mutopia.server.domain.albumReview.dto.AlbumReviewInfoDto( " +
                "CAST(review.id AS long), " +
                "review.title, review.content, review.rating, " +
                "CAST((select count(albumReviewLike) from AlbumReviewLikeEntity albumReviewLike where albumReviewLike.review.id = review.id) AS long), " +
                "review.createdAt, " +
                "album.id, writer.id, writer.username, " +
                "CAST((select profilePicUrl from ProfileEntity profile where profile.user.id = writer.id) AS string), " +
                "album.name, album.artistName, album.coverImageUrl, album.releaseDate, " +
                "album.length, " +
                "CAST((select count(albumReview1) from AlbumReviewEntity albumReview1 where albumReview1.album.id = album.id) AS long), " +
                "CAST((select count(albumLike) from AlbumLikeEntity albumLike where albumLike.album.id = album.id) AS long), " +
                (loginUserId != null ? "CAST((select count(*)>0 from AlbumReviewLikeEntity albumReviewLike2 where albumReviewLike2.review.id = review.id and albumReviewLike2.user.id = :userId) AS boolean ) " : "FALSE ") +
                ") FROM AlbumReviewEntity review " +
                "INNER JOIN review.album album " +
                "INNER JOIN review.writer writer " +
                "WHERE album.id = :albumId " +
                "ORDER BY review.createdAt DESC";


        TypedQuery<AlbumReviewInfoDto> query = em.createQuery(jpql, AlbumReviewInfoDto.class)
                .setParameter("albumId", albumId)
                .setMaxResults(20)
                .setFirstResult(offset);
        if (loginUserId != null) {
            query.setParameter("userId", loginUserId);
        }
        return query.getResultList();


    }

    @Override
    public List<AlbumReviewInfoDto> findAllByAlbumIdOrderByLike(String loginUserId, String albumId, Integer offset) {
        String jpql = "SELECT new site.mutopia.server.domain.albumReview.dto.AlbumReviewInfoDto( " +
                "CAST(review.id AS long), " +
                "review.title, review.content, review.rating, " +
                "CAST((select count(albumReviewLike) from AlbumReviewLikeEntity albumReviewLike where albumReviewLike.review.id = review.id) AS long), " +
                "review.createdAt, " +
                "album.id, writer.id, writer.username, " +
                "CAST((select profilePicUrl from ProfileEntity profile where profile.user.id = writer.id) AS string), " +
                "album.name, album.artistName, album.coverImageUrl, album.releaseDate, " +
                "album.length, " +
                "CAST((select count(albumReview1) from AlbumReviewEntity albumReview1 where albumReview1.album.id = album.id) AS long), " +
                "CAST((select count(albumLike) from AlbumLikeEntity albumLike where albumLike.album.id = album.id) AS long), " +
                (loginUserId != null ? "CAST((select count(*)>0 from AlbumReviewLikeEntity albumReviewLike2 where albumReviewLike2.review.id = review.id and albumReviewLike2.user.id = :loginUserId) AS boolean ) " : "FALSE ") +
                ") FROM AlbumReviewEntity review " +
                "INNER JOIN review.album album " +
                "INNER JOIN review.writer writer " +
                "WHERE album.id = :albumId " +
                "ORDER BY (select count(albumReviewLike) from AlbumReviewLikeEntity albumReviewLike where albumReviewLike.review.id = review.id) DESC";


        TypedQuery<AlbumReviewInfoDto> query = em.createQuery(jpql, AlbumReviewInfoDto.class)
                .setParameter("albumId", albumId)
                .setMaxResults(20)
                .setFirstResult(offset);
        if (loginUserId != null) {
            query.setParameter("userId", loginUserId);
        }
        return query.getResultList();
    }

    @Override
    public List<AlbumReviewInfoDto> findAllOrderByCreatedAtDesc(Integer offset, String loginUserId) {
        String jpql = "SELECT new site.mutopia.server.domain.albumReview.dto.AlbumReviewInfoDto( " +
                "CAST(review.id AS long), " +
                "review.title, review.content, review.rating, " +
                "CAST((select count(albumReviewLike) from AlbumReviewLikeEntity albumReviewLike where albumReviewLike.review.id = review.id) AS long), " +
                "review.createdAt, " +
                "album.id, writer.id, writer.username, " +
                "CAST((select profilePicUrl from ProfileEntity profile where profile.user.id = writer.id) AS string), " +
                "album.name, album.artistName, album.coverImageUrl, album.releaseDate, " +
                "album.length, " +
                "CAST((select count(albumReview1) from AlbumReviewEntity albumReview1 where albumReview1.album.id = album.id) AS long), " +
                "CAST((select count(albumLike) from AlbumLikeEntity albumLike where albumLike.album.id = album.id) AS long), " +
                (loginUserId != null ? "CAST((select count(*)>0 from AlbumReviewLikeEntity albumReviewLike2 where albumReviewLike2.review.id = review.id and albumReviewLike2.user.id = :loginUserId) AS boolean ) " : "FALSE ") +
                ") FROM AlbumReviewEntity review " +
                "INNER JOIN review.album album " +
                "INNER JOIN review.writer writer " +
                "ORDER BY review.createdAt DESC";


        TypedQuery<AlbumReviewInfoDto> query = em.createQuery(jpql, AlbumReviewInfoDto.class)
                .setMaxResults(20)
                .setFirstResult(offset);
        if (loginUserId != null) {
            query.setParameter("loginUserId", loginUserId);
        }
        return query.getResultList();
    }

    @Override
    public List<AlbumReviewInfoDto> findAllOrderByLikeDesc(String loginUserId, Integer offset) {
        String jpql = "SELECT new site.mutopia.server.domain.albumReview.dto.AlbumReviewInfoDto( " +
                "CAST(review.id AS long), " +
                "review.title, review.content, review.rating, " +
                "CAST((select count(albumReviewLike) from AlbumReviewLikeEntity albumReviewLike where albumReviewLike.review.id = review.id) AS long), " +
                "review.createdAt, " +
                "album.id, writer.id, writer.username, " +
                "CAST((select profilePicUrl from ProfileEntity profile where profile.user.id = writer.id) AS string), " +
                "album.name, album.artistName, album.coverImageUrl, album.releaseDate, " +
                "album.length, " +
                "CAST((select count(albumReview1) from AlbumReviewEntity albumReview1 where albumReview1.album.id = album.id) AS long), " +
                "CAST((select count(albumLike) from AlbumLikeEntity albumLike where albumLike.album.id = album.id) AS long), " +
                (loginUserId != null ? "CAST((select count(*)>0 from AlbumReviewLikeEntity albumReviewLike2 where albumReviewLike2.review.id = review.id and albumReviewLike2.user.id = :loginUserId) AS boolean ) " : "FALSE ") +
                ") FROM AlbumReviewEntity review " +
                "INNER JOIN review.album album " +
                "INNER JOIN review.writer writer " +
                "ORDER BY (select count(albumReviewLike) from AlbumReviewLikeEntity albumReviewLike where albumReviewLike.review.id = review.id) DESC";


        TypedQuery<AlbumReviewInfoDto> query = em.createQuery(jpql, AlbumReviewInfoDto.class)
                .setMaxResults(20)
                .setFirstResult(offset);
        if (loginUserId != null) {
            query.setParameter("loginUserId", loginUserId);
        }
        return query.getResultList();
    }

    @Override
    public List<AlbumReviewInfoDto> findLikedByUserIdOrderByCreatedAt(String userId, String loginUserId, Integer offset) {
        String jpql = "SELECT new site.mutopia.server.domain.albumReview.dto.AlbumReviewInfoDto( " +
                "CAST(review.id AS long), " +
                "review.title, review.content, review.rating, " +
                "CAST((select count(albumReviewLike) from AlbumReviewLikeEntity albumReviewLike where albumReviewLike.review.id = review.id) AS long), " +
                "review.createdAt, " +
                "album.id, writer.id, writer.username, " +
                "CAST((select profilePicUrl from ProfileEntity profile where profile.user.id = writer.id) AS string), " +
                "album.name, album.artistName, album.coverImageUrl, album.releaseDate, " +
                "album.length, " +
                "CAST((select count(albumReview1) from AlbumReviewEntity albumReview1 where albumReview1.album.id = album.id) AS long), " +
                "CAST((select count(albumLike) from AlbumLikeEntity albumLike where albumLike.album.id = album.id) AS long), " +
                (loginUserId != null ? "CAST((select count(*)>0 from AlbumReviewLikeEntity albumReviewLike2 where albumReviewLike2.review.id = review.id and albumReviewLike2.user.id = :loginUserId) AS boolean ) " : "FALSE ") +
                ") FROM AlbumReviewEntity review " +
                "INNER JOIN review.album album " +
                "INNER JOIN review.writer writer " +
                "INNER JOIN AlbumReviewLikeEntity albumReviewLike ON review.id = albumReviewLike.review.id " +
                "WHERE albumReviewLike.user.id = :userId " +
                "ORDER BY (select count(albumReviewLike) from AlbumReviewLikeEntity albumReviewLike where albumReviewLike.review.id = review.id) DESC";


        TypedQuery<AlbumReviewInfoDto> query = em.createQuery(jpql, AlbumReviewInfoDto.class)
                .setParameter("userId", userId)
                .setMaxResults(20)
                .setFirstResult(offset);
        if (loginUserId != null) {
            query.setParameter("loginUserId", loginUserId);
        }
        return query.getResultList();
    }


}
