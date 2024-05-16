package site.mutopia.server.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.mutopia.server.domain.albumRating.repository.AlbumRatingRepository;
import site.mutopia.server.domain.albumReview.repository.AlbumReviewRepository;
import site.mutopia.server.domain.follow.repository.FollowRepository;
import site.mutopia.server.domain.profile.entity.ProfileEntity;
import site.mutopia.server.domain.profile.repository.ProfileRepository;
import site.mutopia.server.domain.user.dto.UserAggregationInfoResDto;
import site.mutopia.server.domain.user.exception.UserNotFoundException;
import site.mutopia.server.domain.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final AlbumReviewRepository albumReviewRepository;
    private final AlbumRatingRepository albumRatingRepository;
    private final FollowRepository followRepository;
    private final ProfileRepository profileRepository;

    @Transactional(readOnly = true)
    public UserAggregationInfoResDto aggregateUserInfo(String userId) {
        ProfileEntity profileEntity = profileRepository.findByUserId(userId).orElseThrow(() -> new UserNotFoundException("해당 유저의 프로필이 존재하지 않습니다."));
        Long totalReviewCount = albumReviewRepository.countByWriterId(userId);
        Long totalRatingCount = albumRatingRepository.countByUserId(userId);
        Long followerCount = followRepository.countByFollowingId(userId);
        Long followingCount = followRepository.countByUserId(userId);

        return UserAggregationInfoResDto.builder()
                .totalReviewCount(totalReviewCount)
                .totalRatingCount(totalRatingCount)
                .followerCount(followerCount)
                .followingCount(followingCount)
                .userId(userId)
                .username(profileEntity.getUser().getUsername())
                .profileImageUrl(profileEntity.getProfilePicUrl())
                .build();
    }
}
