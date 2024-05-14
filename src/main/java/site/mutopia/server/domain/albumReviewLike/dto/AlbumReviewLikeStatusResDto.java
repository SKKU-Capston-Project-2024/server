package site.mutopia.server.domain.albumReviewLike.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class AlbumReviewLikeStatusResDto {
    private IsUserLoggedIn isUserLoggedIn;
    private AlbumReviewLikeToggleStatus likeStatus;
    private Long likeCount;

    public enum AlbumReviewLikeToggleStatus {
        ON, OFF, NULL,
    }

    public enum IsUserLoggedIn {
        YES, NO,
    }
}
