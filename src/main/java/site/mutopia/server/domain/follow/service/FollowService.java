package site.mutopia.server.domain.follow.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.mutopia.server.domain.follow.FollowingNotFoundException;
import site.mutopia.server.domain.follow.dto.FollowStatusResponse;
import site.mutopia.server.domain.follow.dto.FollowerInfoDto;
import site.mutopia.server.domain.follow.entity.FollowEntity;
import site.mutopia.server.domain.follow.entity.FollowId;
import site.mutopia.server.domain.follow.repository.FollowRepository;
import site.mutopia.server.domain.user.entity.UserEntity;
import site.mutopia.server.domain.user.exception.UserNotFoundException;
import site.mutopia.server.domain.user.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;


    public List<FollowerInfoDto> getFollowers(String userId, UserEntity loginUser) {
        return followRepository.findFollowerInfoDtoListByUserId(userId, loginUser.getId());
    }

    public List<FollowerInfoDto> getFollowings(String userId, UserEntity loginUser) {
        return followRepository.findFollowingInfoDtoListByUserId(userId, loginUser.getId());
    }

    public void follow(UserEntity loginUser, String userId) {
        UserEntity following = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        followRepository.save(new FollowEntity(loginUser, following));
    }

    public void unfollow(UserEntity loginUser, String userId) {
        UserEntity following = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        followRepository.findById(new FollowId(loginUser.getId(), following.getId()))
                .orElseThrow(() -> new FollowingNotFoundException(loginUser.getId(), following.getId()));
        followRepository.delete(new FollowEntity(loginUser, following));
    }

    public FollowStatusResponse isFollowing(UserEntity loginUser, String userId) {
        return new FollowStatusResponse(followRepository.existsById(new FollowId(loginUser.getId(), userId)));
    }



}
