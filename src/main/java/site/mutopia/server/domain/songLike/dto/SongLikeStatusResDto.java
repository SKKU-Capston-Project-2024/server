package site.mutopia.server.domain.songLike.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class SongLikeStatusResDto {
    private IsUserLoggedIn isUserLoggedIn;
    private SongLikeToggleStatus likeStatus;
    private Long likeCount;

    public enum SongLikeToggleStatus {
        ON, OFF, NULL,
    }

    public enum IsUserLoggedIn {
        YES, NO,
    }
}
