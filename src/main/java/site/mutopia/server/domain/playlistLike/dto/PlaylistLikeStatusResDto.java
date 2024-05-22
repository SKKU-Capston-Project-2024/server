package site.mutopia.server.domain.playlistLike.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class PlaylistLikeStatusResDto {
    private IsUserLoggedIn isUserLoggedIn;
    private PlaylistLikeToggleStatus likeStatus;
    private Long likeCount;

    public enum PlaylistLikeToggleStatus {
        ON, OFF, NULL,
    }

    public enum IsUserLoggedIn {
        YES, NO,
    }
}
