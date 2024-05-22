package site.mutopia.server.domain.songLike.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SongLikeCustomRepositoryImpl implements SongLikeCustomRepository {

    private final EntityManager entityManager;

    @Override
    public boolean existsBySongIdAndUserId(String songId, String userId) {
        Query query = entityManager.createQuery(
                "SELECT count(al) FROM SongLikeEntity al WHERE al.song.id = :songId AND al.user.id = :userId");
        query.setParameter("songId", songId);
        query.setParameter("userId", userId);
        return (Long) query.getSingleResult() > 0;
    }

    @Override
    public void deleteBySongIdAndUserId(String songId, String userId) {
        Query query = entityManager.createQuery(
                "DELETE FROM SongLikeEntity al WHERE al.song.id = :songId AND al.user.id = :userId");
        query.setParameter("songId", songId);
        query.setParameter("userId", userId);
        query.executeUpdate();
    }
}
