package site.mutopia.server.domain.user.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserAggregationInfoResDto {
    private Long totalReviewCount;
    private Long totalRatingCount;
    private Long followerCount;
    private Long followingCount;
}
