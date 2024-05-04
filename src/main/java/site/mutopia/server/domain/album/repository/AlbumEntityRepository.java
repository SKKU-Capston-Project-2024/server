package site.mutopia.server.domain.album.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.mutopia.server.domain.album.entity.AlbumEntity;

@Repository
public interface AlbumEntityRepository extends JpaRepository<AlbumEntity, String>{
}
