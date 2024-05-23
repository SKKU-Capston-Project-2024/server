package site.mutopia.server.domain.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import site.mutopia.server.domain.auth.entity.GoogleTokenEntity;

import java.util.Optional;

public interface GoogleTokenRepository extends JpaRepository<GoogleTokenEntity, Long> {

    @Query("SELECT gt.tokenValue FROM GoogleTokenEntity gt WHERE gt.user.id = :userId AND gt.tokenType = site.mutopia.server.domain.auth.entity.GoogleTokenType.ACCESS")
    Optional<String> findAccessTokenByUserId(@Param("userId") String userId);
}

