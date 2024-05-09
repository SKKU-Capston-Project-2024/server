package site.mutopia.server.domain.song.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.mutopia.server.domain.song.entity.SongEntity;

public interface SongRepository extends JpaRepository<SongEntity, String> {
}
