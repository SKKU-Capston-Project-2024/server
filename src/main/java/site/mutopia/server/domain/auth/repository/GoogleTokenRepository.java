package site.mutopia.server.domain.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.mutopia.server.domain.auth.entity.GoogleTokenEntity;

public interface GoogleTokenRepository extends JpaRepository<GoogleTokenEntity, Long> {}

