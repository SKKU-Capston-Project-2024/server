package site.mutopia.server.domain.albumReviewLike.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AlbumReviewLikeCustomRepositoryImpl implements AlbumReviewLikeCustomRepository {

    private final EntityManager entityManager;

    @Override
    public boolean existsByAlbumReviewIdAndUserId(Long albumReviewId, String userId) {
        Query query = entityManager.createQuery(
                "SELECT count(arl) FROM AlbumReviewLikeEntity arl WHERE arl.review.id = :albumReviewId AND arl.user.id = :userId");
        query.setParameter("albumReviewId", albumReviewId);
        query.setParameter("userId", userId);
        return (Long) query.getSingleResult() > 0;
    }

    @Override
    public void deleteByAlbumReviewIdAndUserId(Long albumReviewId, String userId) {
        Query query = entityManager.createQuery(
                "DELETE FROM AlbumReviewLikeEntity arl WHERE arl.review.id = :albumReviewId AND arl.user.id = :userId");
        query.setParameter("albumReviewId", albumReviewId);
        query.setParameter("userId", userId);
        query.executeUpdate();
    }
}
