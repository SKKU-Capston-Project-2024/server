package site.mutopia.server.domain.albumLike.dto;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AlbumLikeStatusResDto {
    private AlbumLikeStatus status;

    public AlbumLikeStatusResDto(boolean status) {
        if(status) this.status = AlbumLikeStatus.ON;
        else this.status = AlbumLikeStatus.OFF;
    }

    public enum AlbumLikeStatus {
        ON, OFF
    }
}
