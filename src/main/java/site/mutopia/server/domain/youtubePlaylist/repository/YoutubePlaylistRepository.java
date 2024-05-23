package site.mutopia.server.domain.youtubePlaylist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.mutopia.server.domain.youtubePlaylist.entity.YoutubePlaylistEntity;

import java.util.List;

public interface YoutubePlaylistRepository extends JpaRepository<YoutubePlaylistEntity, String> {

    List<YoutubePlaylistEntity> findAllByCreatorId(String creatorId);

}
