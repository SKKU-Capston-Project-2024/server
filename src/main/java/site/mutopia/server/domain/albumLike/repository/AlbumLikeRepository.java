package site.mutopia.server.domain.albumLike.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.mutopia.server.domain.album.entity.AlbumEntity;
import site.mutopia.server.domain.albumLike.entity.AlbumLikeEntity;
import site.mutopia.server.domain.albumLike.entity.AlbumLikeId;
import site.mutopia.server.domain.user.entity.UserEntity;

import java.util.Optional;

@Repository
public interface AlbumLikeRepository extends JpaRepository<AlbumLikeEntity, AlbumLikeId>, AlbumLikeCustomRepository {
    Optional<AlbumLikeEntity> findByAlbumAndUser(AlbumEntity album, UserEntity user);
}
