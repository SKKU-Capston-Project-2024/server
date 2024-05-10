package site.mutopia.server.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.mutopia.server.domain.albumRating.repository.AlbumRatingRepository;
import site.mutopia.server.domain.albumReview.repository.AlbumReviewRepository;
import site.mutopia.server.domain.follow.repository.FollowRepository;
import site.mutopia.server.domain.user.dto.UserAggregationInfoResDto;

@Service
@RequiredArgsConstructor
public class UserService {
    private final AlbumReviewRepository albumReviewRepository;
    private final AlbumRatingRepository albumRatingRepository;
    private final FollowRepository followRepository;

    @Transactional(readOnly = true)
    public UserAggregationInfoResDto aggregateUserInfo(String userId) {
        Long totalReviewCount = albumReviewRepository.countByWriterId(userId);
        Long totalRatingCount = albumRatingRepository.countByUserId(userId);
        Long followerCount = followRepository.countByFollowingId(userId);
        Long followingCount = followRepository.countByUserId(userId);

        return UserAggregationInfoResDto.builder()
                .totalReviewCount(totalReviewCount)
                .totalRatingCount(totalRatingCount)
                .followerCount(followerCount)
                .followingCount(followingCount)
                .build();
    }
}
