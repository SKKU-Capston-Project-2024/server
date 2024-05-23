package site.mutopia.server.domain.songLike.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import site.mutopia.server.domain.songLike.entity.SongLikeEntity;

@AllArgsConstructor
@Getter
public class LikeSongDto {
    String songId;
    String albumId;
    String albumCoverImg;
    String songTitle;
    String artistName;
    String albumName;

    public static LikeSongDto of(SongLikeEntity entity){
        return new LikeSongDto(
                entity.getSong().getId(),
                entity.getSong().getAlbum().getId(),
                entity.getSong().getAlbum().getCoverImageUrl(),
                entity.getSong().getTitle(),
                entity.getSong().getAlbum().getArtistName(),
                entity.getSong().getAlbum().getName()
        );
    }
}
