package site.mutopia.server.domain.albumLike.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class AlbumLikeStatusResDto {
    private IsUserLoggedIn isUserLoggedIn;
    private AlbumLikeToggleStatus likeStatus;
    private Long likeCount;

    public enum AlbumLikeToggleStatus {
        ON, OFF, NULL,
    }

    public enum IsUserLoggedIn {
        YES, NO,
    }
}
