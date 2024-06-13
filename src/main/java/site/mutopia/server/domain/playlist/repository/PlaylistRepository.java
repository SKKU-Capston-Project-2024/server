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
            "p.id, p.creator.id, p.creator.username, CAST((SELECT COUNT(pl) FROM PlaylistLikeEntity pl WHERE pl.playlist.id = p.id) AS long), false, p.title, p.content, p.createdAt)" +
            "FROM PlaylistEntity p " +
            "WHERE p.creator.id = :userId")
    Page<PlaylistInfoDto> findPlaylistInfosByUserId(@Param("userId") String userId, Pageable pageable);


    @Query("SELECT new site.mutopia.server.domain.playlist.dto.PlaylistInfoDto(" +
            "p.id, p.creator.id, p.creator.username, CAST((SELECT COUNT(pl) FROM PlaylistLikeEntity pl WHERE pl.playlist.id = p.id) AS long), false , p.title, p.content, p.createdAt)" +
            "FROM PlaylistEntity p " +
            "WHERE p.id = :playlistId")
    Optional<PlaylistInfoDto> findPlaylistInfoById(@Param("playlistId") Long playlistId);

    @Query("SELECT new site.mutopia.server.domain.playlist.dto.PlaylistInfoDto(" +
            "p.id, p.creator.id, p.creator.username, CAST((SELECT COUNT(pl) FROM PlaylistLikeEntity pl WHERE pl.playlist.id = p.id) AS long), false , p.title, p.content, p.createdAt)" +
            "FROM PlaylistEntity p " +
            "ORDER BY p.createdAt DESC")
    Page<PlaylistInfoDto> findRecentPlaylists(Pageable pageable);


    @Query("SELECT new site.mutopia.server.domain.playlist.dto.PlaylistInfoDto(" +
            "p.id, p.creator.id, p.creator.username, CAST((SELECT COUNT(pl) FROM PlaylistLikeEntity pl WHERE pl.playlist.id = p.id) AS long), false , p.title, p.content, p.createdAt)" +
            "FROM PlaylistEntity p " +
            "ORDER BY CAST((SELECT COUNT(pl) FROM PlaylistLikeEntity pl WHERE pl.playlist.id = p.id) AS long) DESC")
    Page<PlaylistInfoDto> findPopularPlaylist(Pageable pageable);


    @Query("SELECT new site.mutopia.server.domain.playlist.dto.PlaylistInfoDto(" +
            "p.id, p.creator.id, p.creator.username, CAST((SELECT COUNT(pl) FROM PlaylistLikeEntity pl WHERE pl.playlist.id = p.id) AS long), false, p.title, p.content, p.createdAt)" +
            "FROM PlaylistLikeEntity pll " +
            "JOIN pll.playlist p " +
            "JOIN p.songs " +
            "WHERE pll.user.id = :userId " +
            "GROUP BY p.id " +
            "ORDER BY p.createdAt DESC")
    Page<PlaylistInfoDto> findLikedPlayList(@Param("userId") String userId, Pageable pageable);


    @Query("SELECT new site.mutopia.server.domain.playlist.dto.PlaylistInfoDto(" +
            "ps.playlist.id, ps.playlist.creator.id, ps.playlist.creator.username, " +
            "CAST((SELECT COUNT(pl) FROM PlaylistLikeEntity pl WHERE pl.playlist.id = ps.playlist.id) AS long), " +
            "false, ps.playlist.title, ps.playlist.content, ps.playlist.createdAt) " +
            "FROM PlaylistSongEntity ps " +
            "JOIN ps.playlist p " +
            "JOIN p.songs s " +
            "WHERE s.song.album.id = :albumId "+
            "GROUP BY p.id " +
            "ORDER BY p.createdAt DESC")
    Page<PlaylistInfoDto> findByAlbumId(@Param("albumId") String albumId, Pageable pageable);



    @Query("SELECT new site.mutopia.server.domain.playlist.dto.PlaylistInfoDto(" +
            "ps.playlist.id, ps.playlist.creator.id, ps.playlist.creator.username, " +
            "CAST((SELECT COUNT(pl) FROM PlaylistLikeEntity pl WHERE pl.playlist.id = ps.playlist.id) AS long), " +
            "false, ps.playlist.title, ps.playlist.content, ps.playlist.createdAt) " +
            "FROM PlaylistSongEntity ps " +
            "JOIN ps.playlist p " +
            "JOIN p.songs s " +
            "WHERE s.song.id = :songId " +
            "GROUP BY p.id " +
            "ORDER BY p.createdAt DESC")
    Page<PlaylistInfoDto> findBySongId(@Param("songId") String songId, Pageable pageable);

    @Query("SELECT new site.mutopia.server.domain.playlist.dto.PlaylistInfoDto(" +
            "ps.playlist.id, ps.playlist.creator.id, ps.playlist.creator.username, " +
            "CAST((SELECT COUNT(pl) FROM PlaylistLikeEntity pl WHERE pl.playlist.id = ps.playlist.id) AS long), " +
            "false, ps.playlist.title, ps.playlist.content, ps.playlist.createdAt) " +
            "FROM PlaylistSongEntity ps " +
            "JOIN ps.playlist p " +
            "JOIN p.songs s " +
            "WHERE s.song.album.id = :albumId "+
            "GROUP BY p.id " +
            "ORDER BY CAST((SELECT COUNT(pl) FROM PlaylistLikeEntity pl WHERE pl.playlist.id = ps.playlist.id) AS long) DESC")
    Page<PlaylistInfoDto> findByAlbumIdDescPopular(@Param("albumId") String albumId, Pageable pageable);



    @Query("SELECT new site.mutopia.server.domain.playlist.dto.PlaylistInfoDto(" +
            "ps.playlist.id, ps.playlist.creator.id, ps.playlist.creator.username, " +
            "CAST((SELECT COUNT(pl) FROM PlaylistLikeEntity pl WHERE pl.playlist.id = ps.playlist.id) AS long), " +
            "false, ps.playlist.title, ps.playlist.content, ps.playlist.createdAt) " +
            "FROM PlaylistSongEntity ps " +
            "JOIN ps.playlist p " +
            "JOIN p.songs s " +
            "WHERE s.song.id = :songId " +
            "GROUP BY p.id " +
            "ORDER BY CAST((SELECT COUNT(pl) FROM PlaylistLikeEntity pl WHERE pl.playlist.id = ps.playlist.id) AS long) DESC")
    Page<PlaylistInfoDto> findBySongIdDescPopular(@Param("songId") String songId, Pageable pageable);


}
