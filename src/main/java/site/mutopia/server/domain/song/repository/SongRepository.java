package site.mutopia.server.domain.song.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.mutopia.server.domain.song.entity.SongEntity;

@Repository
public interface SongRepository extends JpaRepository<SongEntity, String>, SongCustomRepository {
}
