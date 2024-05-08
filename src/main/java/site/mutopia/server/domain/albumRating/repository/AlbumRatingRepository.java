package site.mutopia.server.domain.albumRating.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.mutopia.server.domain.albumLike.entity.AlbumLikeId;
import site.mutopia.server.domain.albumRating.entity.AlbumRatingEntity;

@Repository
public interface AlbumRatingRepository extends JpaRepository<AlbumRatingEntity, AlbumLikeId> {
}
