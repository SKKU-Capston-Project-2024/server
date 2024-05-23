package site.mutopia.server.domain.albumLike.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import site.mutopia.server.domain.albumLike.entity.AlbumLikeEntity;

@AllArgsConstructor
@Data
public class LikeAlbumDto {
    String albumId;
    String albumName;
    String artistName;
    String albumCoverImg;

    public static LikeAlbumDto toDto(AlbumLikeEntity entity){
        return new LikeAlbumDto(entity.getAlbum().getId(), entity.getAlbum().getName(), entity.getAlbum().getArtistName(), entity.getAlbum().getCoverImageUrl());
    }
}
