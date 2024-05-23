package site.mutopia.server.domain.songCommentLike.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class SongCommentLikeStatusResDto {
    private IsUserLoggedIn isUserLoggedIn;
    private SongCommentLikeToggleStatus likeStatus;
    private Long likeCount;

    public enum SongCommentLikeToggleStatus {
        ON, OFF, NULL,
    }

    public enum IsUserLoggedIn {
        YES, NO,
    }
}
