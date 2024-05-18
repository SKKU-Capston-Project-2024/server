package site.mutopia.server.domain.topster.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.mutopia.server.domain.topster.entity.TopsterEntity;

import java.util.Optional;

@Repository
public interface TopsterRepository extends JpaRepository<TopsterEntity, Long> {

    Optional<TopsterEntity> findByUserId(String userId);
}
