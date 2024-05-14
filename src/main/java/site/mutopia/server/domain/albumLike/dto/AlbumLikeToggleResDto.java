package site.mutopia.server.domain.albumLike.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AlbumLikeToggleResDto {
    private AlbumLikeToggleResStatus likeStatus;
    private Long likeCount;

    public enum AlbumLikeToggleResStatus {
        ON, OFF, NULL,
    }
}



