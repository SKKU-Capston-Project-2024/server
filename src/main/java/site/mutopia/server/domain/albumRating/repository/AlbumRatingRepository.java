package site.mutopia.server.domain.albumRating.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.mutopia.server.domain.albumRating.entity.AlbumRatingEntity;
import site.mutopia.server.domain.albumRating.entity.AlbumRatingId;
import java.util.Optional;

@Repository
public interface AlbumRatingRepository extends JpaRepository<AlbumRatingEntity, AlbumRatingId> {

    Optional<AlbumRatingEntity> findByUserIdAndAlbumId(String userId, String albumId);
}
