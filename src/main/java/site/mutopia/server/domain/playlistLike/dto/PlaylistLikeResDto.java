package site.mutopia.server.domain.playlistLike.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class PlaylistLikeResDto {

    private PlaylistLikeToggleResStatus likeStatus;
    private Long likeCount;

    public enum PlaylistLikeToggleResStatus {
        ON, OFF
    }
}
