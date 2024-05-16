package site.mutopia.server.domain.profile.dto.response;

import lombok.*;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProfileAggregationInfoResDto {
    private String userId;
    private String username;
    private String profileImageUrl;
    private Long totalReviewCount;
    private Long totalRatingCount;
    private Long followerCount;
    private Long followingCount;
}
