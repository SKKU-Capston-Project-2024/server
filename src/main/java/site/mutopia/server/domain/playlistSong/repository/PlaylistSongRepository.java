package site.mutopia.server.domain.playlistSong.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.mutopia.server.domain.playlistSong.entity.PlaylistSongEntity;
import site.mutopia.server.domain.playlistSong.entity.PlaylistSongId;

import java.util.List;

@Repository
public interface PlaylistSongRepository extends JpaRepository<PlaylistSongEntity, PlaylistSongId> {

    List<PlaylistSongEntity> findByPlaylistId(Long playlistId);

    void deleteByPlaylistIdAndSongId(Long playlistId, String songId);

    void deleteByPlaylistId(Long playlistId);
}
