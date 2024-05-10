package site.mutopia.server.domain.albumReview.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import site.mutopia.server.domain.albumReview.dto.AlbumReviewInfoDto;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class AlbumReviewCustomRepositoryImpl implements AlbumReviewCustomRepository {

    private final EntityManager em;

    @Override
    public Optional<AlbumReviewInfoDto> findAlbumReviewInfoDto(Long albumReviewId) {
        String jpql = "SELECT new site.mutopia.server.domain.albumReview.dto.AlbumReviewInfoDto(" +
                "review.title, review.content, review.rating, " +
                "album.id, writer.id, writer.username, " +
                "album.name, album.artistName, album.coverImageUrl, album.releaseDate, " +
                "album.length, " +
                "CAST((select count(albumReview1) from AlbumReviewEntity albumReview1 where albumReview1.album.id = album.id) AS long), " +
                "CAST((select avg(albumRating.rating) from AlbumRatingEntity albumRating where albumRating.album.id = album.id) AS double), " +
                "CAST((select count(albumLike) from AlbumLikeEntity albumLike where albumLike.album.id = album.id) AS long))" +
                "FROM AlbumReviewEntity review " +
                "INNER JOIN review.album album " +
                "INNER JOIN review.writer writer " +
                "WHERE review.id = :albumReviewId";

        try {
            AlbumReviewInfoDto result = em.createQuery(jpql, AlbumReviewInfoDto.class)
                    .setParameter("albumReviewId", albumReviewId)
                    .getSingleResult();
            return Optional.of(result);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<AlbumReviewInfoDto> findAlbumReviewInfoDtoListByUserId(String userId, Integer limit) {
        String jpql = "SELECT new site.mutopia.server.domain.albumReview.dto.AlbumReviewInfoDto(" +
                "review.title, review.content, review.rating, " +
                "album.id, writer.id, writer.username, " +
                "album.name, album.artistName, album.coverImageUrl, album.releaseDate, " +
                "album.length, " +
                "CAST((select count(albumReview1) from AlbumReviewEntity albumReview1 where albumReview1.album.id = album.id) AS long), " +
                "CAST((select avg(albumRating.rating) from AlbumRatingEntity albumRating where albumRating.album.id = album.id) AS double), " +
                "CAST((select count(albumLike) from AlbumLikeEntity albumLike where albumLike.album.id = album.id) AS long)) " +
                "FROM AlbumReviewEntity review " +
                "INNER JOIN review.album album " +
                "INNER JOIN review.writer writer " +
                "WHERE writer.id = :userId";

        return em.createQuery(jpql, AlbumReviewInfoDto.class)
                .setParameter("userId", userId)
                .setMaxResults(limit)
                .getResultList();
    }


}
