package site.mutopia.server.domain.profile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.mutopia.server.domain.profile.entity.ProfileEntity;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<ProfileEntity, String> {
    Optional<ProfileEntity> findByUserUserId(String userId);
}
