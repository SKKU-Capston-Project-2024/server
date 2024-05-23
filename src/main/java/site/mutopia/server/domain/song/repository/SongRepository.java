package site.mutopia.server.domain.song.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import site.mutopia.server.domain.song.entity.SongEntity;

import java.util.List;

public interface SongRepository extends JpaRepository<SongEntity, String> {

    @Query("SELECT ps.song FROM PlaylistSongEntity ps WHERE ps.playlist.id = :playlistId")
    List<SongEntity> findSongsByPlaylistId(@Param("playlistId") Long playlistId);
}
