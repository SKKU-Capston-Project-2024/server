package site.mutopia.server.domain.albumLike.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AlbumLikeToggleResDto {
    private AlbumLikeToggleStatus likeStatus;

    public enum AlbumLikeToggleStatus {
        ON, OFF,
    }
}



