package site.mutopia.server.spotify.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.mutopia.server.spotify.entity.SpotifyTokenEntity;
import site.mutopia.server.spotify.entity.SpotifyTokenId;
import site.mutopia.server.spotify.entity.SpotifyTokenType;

import java.util.Optional;

public interface SpotifyTokenRepository extends JpaRepository<SpotifyTokenEntity, SpotifyTokenId> {
    Optional<SpotifyTokenEntity> findByUserIdAndTokenType(String userId, SpotifyTokenType tokenType);
}
