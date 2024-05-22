package site.mutopia.server.domain.playlistLike.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import site.mutopia.server.domain.playlistLike.entity.PlaylistLikeEntity;
import site.mutopia.server.domain.playlistLike.entity.PlaylistLikeId;

public interface PlaylistLikeRepository extends JpaRepository<PlaylistLikeEntity, PlaylistLikeId> {

    void deleteByUserIdAndPlaylistId(String userId, Long playlistId);

    boolean existsByUserIdAndPlaylistId(String userId, Long playlistId);

    @Query("select count(pll) from PlaylistLikeEntity pll where pll.playlist.id = :playlistId")
    Long countLikesByPlaylistId(@Param("playlistId") Long playlistId);
}