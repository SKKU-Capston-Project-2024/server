package site.mutopia.server.domain.playlist.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import site.mutopia.server.domain.playlist.dto.PlaylistInfoDto;
import site.mutopia.server.domain.playlist.entity.PlaylistEntity;

import java.util.Optional;

public interface PlaylistRepository extends JpaRepository<PlaylistEntity, Long> {

    @Query("SELECT new site.mutopia.server.domain.playlist.dto.PlaylistInfoDto(" +
            "p.id, p.creator.id, p.creator.username, CAST((SELECT COUNT(pl) FROM PlaylistLikeEntity pl WHERE pl.playlist.id = p.id) AS long), (SELECT COUNT(pl) > 0 FROM PlaylistLikeEntity pl WHERE pl.user.id = :userId AND pl.playlist.id = p.id), p.title, p.content, p.createdAt)" +
            "FROM PlaylistEntity p " +
            "WHERE p.creator.id = :userId")
    Page<PlaylistInfoDto> findPlaylistInfosByUserId(@Param("userId") String userId, Pageable pageable);


    @Query("SELECT new site.mutopia.server.domain.playlist.dto.PlaylistInfoDto(" +
            "p.id, p.creator.id, p.creator.username, CAST((SELECT COUNT(pl) FROM PlaylistLikeEntity pl WHERE pl.playlist.id = p.id) AS long), (SELECT COUNT(pl) > 0 FROM PlaylistLikeEntity pl WHERE pl.user.id = :userId AND pl.playlist.id = p.id), p.title, p.content, p.createdAt)" +
            "FROM PlaylistEntity p " +
            "WHERE p.id = :playlistId")
    Optional<PlaylistInfoDto> findPlaylistInfoById(@Param("playlistId") Long playlistId, @Param("userId") String userId);

    @Query("SELECT new site.mutopia.server.domain.playlist.dto.PlaylistInfoDto(" +
            "p.id, p.creator.id, p.creator.username, CAST((SELECT COUNT(pl) FROM PlaylistLikeEntity pl WHERE pl.playlist.id = p.id) AS long), (SELECT COUNT(pl) > 0 FROM PlaylistLikeEntity pl WHERE pl.user.id = :userId AND pl.playlist.id = p.id), p.title, p.content, p.createdAt)" +
            "FROM PlaylistEntity p " +
            "ORDER BY p.createdAt DESC")
    Page<PlaylistInfoDto> findRecentPlaylists(Pageable pageable, @Param("userId") String userId);


    @Query("SELECT new site.mutopia.server.domain.playlist.dto.PlaylistInfoDto(" +
            "p.id, p.creator.id, p.creator.username, COUNT(pll), (SELECT COUNT(pl) > 0 FROM PlaylistLikeEntity pl WHERE pl.user.id = :userId AND pl.playlist.id = p.id), p.title, p.content, p.createdAt)" +
            "FROM PlaylistLikeEntity pll " +
            "JOIN pll.playlist p " +
            "JOIN p.songs " +
            "WHERE pll.user.id = :userId " +
            "GROUP BY p.id " +
            "ORDER BY p.createdAt DESC")
    Page<PlaylistInfoDto> findLikedPlayList(@Param("userId") String userId, Pageable pageable);
}
