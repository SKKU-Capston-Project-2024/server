package site.mutopia.server.domain.profile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.mutopia.server.domain.profile.entity.ProfileEntity;

public interface ProfileRepository extends JpaRepository<ProfileEntity, String> {

}
