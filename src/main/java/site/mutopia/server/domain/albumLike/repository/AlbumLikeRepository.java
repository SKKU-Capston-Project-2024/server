package site.mutopia.server.domain.albumLike.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.mutopia.server.domain.albumLike.entity.AlbumLikeEntity;
import site.mutopia.server.domain.albumLike.entity.AlbumLikeId;

@Repository
public interface AlbumLikeRepository extends JpaRepository<AlbumLikeEntity, AlbumLikeId>, AlbumLikeCustomRepository {}
