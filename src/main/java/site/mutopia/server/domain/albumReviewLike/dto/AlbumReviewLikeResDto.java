package site.mutopia.server.domain.albumReviewLike.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class AlbumReviewLikeResDto {
    private AlbumReviewLikeToggleResStatus likeStatus;
    private Long likeCount;

    public enum AlbumReviewLikeToggleResStatus {
        ON, OFF, NULL
    }
}
