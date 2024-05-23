package site.mutopia.server.domain.albumLike.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import site.mutopia.server.domain.albumLike.dto.LikeAlbumDto;
import site.mutopia.server.domain.albumLike.entity.AlbumLikeEntity;
import site.mutopia.server.domain.albumLike.entity.AlbumLikeId;

import java.util.List;

@Repository
public interface AlbumLikeRepository extends JpaRepository<AlbumLikeEntity, AlbumLikeId>, AlbumLikeCustomRepository {

    @Query("SELECT al FROM AlbumLikeEntity al JOIN FETCH al.album WHERE al.user.id = :userId")
    List<AlbumLikeEntity> findLikedAlbumsByUserId(@Param("userId") String userId, Pageable pageable);

}
