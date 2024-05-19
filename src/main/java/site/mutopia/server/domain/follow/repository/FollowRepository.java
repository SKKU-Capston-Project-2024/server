package site.mutopia.server.domain.follow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import site.mutopia.server.domain.follow.dto.FollowerInfoDto;
import site.mutopia.server.domain.follow.entity.FollowEntity;
import site.mutopia.server.domain.follow.entity.FollowId;

import java.util.List;

@Repository
public interface FollowRepository extends JpaRepository<FollowEntity, FollowId> {
    Long countByFollowingId(String followingUserId);
    Long countByUserId(String userId);

    @Query("SELECT new site.mutopia.server.domain.follow.dto.FollowerInfoDto(:userId, p.profilePicUrl, u.username) FROM FollowEntity f JOIN ProfileEntity p ON f.following.id = p.user.id JOIN UserEntity u ON f.following.id = u.id WHERE f.following.id = :userId")
    List<FollowerInfoDto> findFollowerInfoDtoListByUserId(@Param("userId") String userId);
}