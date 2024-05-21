package site.mutopia.server.domain.songLike.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SongLikeToggleResDto {
    private SongLikeToggleResStatus likeStatus;
    private Long likeCount;

    public enum SongLikeToggleResStatus {
        ON, OFF, NULL,
    }
}



