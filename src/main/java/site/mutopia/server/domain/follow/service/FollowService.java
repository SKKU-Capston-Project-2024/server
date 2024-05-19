package site.mutopia.server.domain.follow.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.mutopia.server.domain.follow.dto.FollowerInfoDto;
import site.mutopia.server.domain.follow.repository.FollowRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;

    public List<FollowerInfoDto> getFollowers(String userId) {
        return followRepository.findFollowerInfoDtoListByUserId(userId);
    }



}
