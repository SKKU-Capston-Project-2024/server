package site.mutopia.server.domain.songCommentLike.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SongCommentLikeToggleResDto {
    private SongCommentLikeToggleResStatus likeStatus;
    private Long likeCount;

    public enum SongCommentLikeToggleResStatus {
        ON, OFF, NULL,
    }
}



