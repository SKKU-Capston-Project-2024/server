package site.mutopia.server.domain.playlist.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import site.mutopia.server.domain.playlist.dto.PlaylistInfoDto;
import site.mutopia.server.domain.playlist.entity.PlaylistEntity;

@Repository
public interface PlaylistRepository extends JpaRepository<PlaylistEntity, Long> {

    @Query("SELECT new site.mutopia.server.domain.playlist.dto.PlaylistInfoDto(" +
            "p.id, p.creator.id, " +
            "(SELECT COUNT(pl) FROM PlaylistLikeEntity pl WHERE pl.playlist.id = p.id)) " +
            "FROM PlaylistEntity p " +
            "WHERE p.creator.id = :userId")
    Page<PlaylistInfoDto> findPlaylistInfoByUserId(@Param("userId") String userId, Pageable pageable);
}
