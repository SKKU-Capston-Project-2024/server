package site.mutopia.server.domain.topster.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.mutopia.server.domain.topster.entity.TopsterAlbumEntity;
import site.mutopia.server.domain.topster.entity.TopsterAlbumId;

import java.util.List;

@Repository
public interface TopsterAlbumRepository extends JpaRepository<TopsterAlbumEntity, TopsterAlbumId> {
    List<TopsterAlbumEntity> findByTopsterId(Long topsterId);
}
