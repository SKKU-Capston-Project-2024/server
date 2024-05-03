package site.mutopia.server.domain.album.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.mutopia.server.domain.album.domain.MutopiaAlbum;

import java.util.List;

@Repository
public interface MutopiaAlbumRepository extends JpaRepository<MutopiaAlbum, String>{
}
