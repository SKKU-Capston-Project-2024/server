package site.mutopia.server.domain.topster.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import site.mutopia.server.domain.topster.entity.TopsterEntity;

import java.util.Optional;

@Repository
public interface TopsterRepository extends JpaRepository<TopsterEntity, Long> {

    @Query("SELECT COUNT(t) > 0 FROM TopsterEntity t WHERE t.user.id = :userId AND t.id = :topsterId")
    boolean existsByUserIdAndTopsterId(@Param("userId") String userId, @Param("topsterId") Long topsterId);

    Optional<TopsterEntity> findFirstByUserIdOrderByCreatedAtDesc(String userId);
}
