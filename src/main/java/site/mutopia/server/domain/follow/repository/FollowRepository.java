package site.mutopia.server.domain.follow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.mutopia.server.domain.follow.entity.FollowEntity;
import site.mutopia.server.domain.follow.entity.FollowId;

@Repository
public interface FollowRepository extends JpaRepository<FollowEntity, FollowId> {
    Long countByFollowingId(String followingUserId);
    Long countByUserId(String userId);
}