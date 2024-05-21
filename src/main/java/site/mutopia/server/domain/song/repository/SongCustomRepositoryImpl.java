package site.mutopia.server.domain.song.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import site.mutopia.server.domain.song.dto.SongInfoDto;

import java.util.Optional;

@RequiredArgsConstructor
public class SongCustomRepositoryImpl implements SongCustomRepository{

    private final EntityManager em;

    @Override
    public Optional<SongInfoDto> findInfoById(String loginUserId, String songId) {
        String jpql = "SELECT new site.mutopia.server.domain.song.dto.SongInfoDto(" +
                "s.id, s.album.id, s.album.name, s.album.coverImageUrl, s.album.artistName, " +
                "CAST((SELECT COUNT(*) FROM SongCommentEntity sc WHERE sc.song.id = s.id) AS long), " +
                "CAST((SELECT AVG(sc.rating) FROM SongCommentEntity sc WHERE sc.song.id = s.id) AS double), " +
                "CAST((SELECT COUNT(sl) FROM SongLikeEntity sl WHERE sl.song.id = s.id) AS long), " +
                (loginUserId == null ? "false " : "CAST((COUNT(*) > 0 FROM SongLikeEntity sl WHERE sl.song.id = s.id AND sl.user.id = :loginUserId) AS boolean) ") +
                (loginUserId == null ? "false " : "CAST((sc.rating FROM SongCommentEntity sc WHERE sc.song.id = s.id AND sc.writer.id = :loginUserId) AS Long) ") +
                "FROM SongEntity s " +
                "WHERE s.id = :songId";


        TypedQuery<SongInfoDto> query = em.createQuery(jpql, SongInfoDto.class)
                .setParameter("songId", songId);

        if (loginUserId != null) {
            query.setParameter("loginUserId", loginUserId);
        }

        try {
            SongInfoDto singleResult = query.getSingleResult();
            return Optional.of(singleResult);
        } catch (NoResultException e) {
            return Optional.empty();
        }

    }
}
