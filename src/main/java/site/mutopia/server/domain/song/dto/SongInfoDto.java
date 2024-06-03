package site.mutopia.server.domain.song.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import site.mutopia.server.domain.song.entity.SongEntity;

@Getter
@Setter
@AllArgsConstructor
public class SongInfoDto {
    String id;
    String trackName;
    String albumId;
    String albumName;
    String albumCoverUrl;
    String artistName;
    Long commentCount;
    Double averageRating;
    Long likeCount;
    Boolean isLiked;
    Long myRating;

    public static SongInfoDto fromEntity(SongEntity entity){
        return new SongInfoDto(
                entity.getId(),
                entity.getTitle(),
                entity.getAlbum().getId(),
                entity.getAlbum().getName(),
                entity.getAlbum().getCoverImageUrl(),
                entity.getAlbum().getArtistName(),
                0L,
                entity.getAverageRating(),
                0L,
                false,
                0L
        );
    }
}
