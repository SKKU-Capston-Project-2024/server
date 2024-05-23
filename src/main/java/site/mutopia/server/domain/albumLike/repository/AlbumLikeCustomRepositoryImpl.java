package site.mutopia.server.domain.albumLike.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AlbumLikeCustomRepositoryImpl implements AlbumLikeCustomRepository{

    private final EntityManager entityManager;

    @Override
    public boolean existsByAlbumIdAndUserId(String albumId, String userId) {
        Query query = entityManager.createQuery(
                "SELECT count(al) FROM AlbumLikeEntity al WHERE al.album.id = :albumId AND al.user.id = :userId");
        query.setParameter("albumId", albumId);
        query.setParameter("userId", userId);
        return (Long) query.getSingleResult() > 0;
    }

    @Override
    public void deleteByAlbumIdAndUserId(String albumId, String userId) {
        Query query = entityManager.createQuery(
                "DELETE FROM AlbumLikeEntity al WHERE al.album.id = :albumId AND al.user.id = :userId");
        query.setParameter("albumId", albumId);
        query.setParameter("userId", userId);
        query.executeUpdate();
    }
}
