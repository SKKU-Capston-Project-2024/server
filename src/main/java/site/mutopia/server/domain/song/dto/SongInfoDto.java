package site.mutopia.server.domain.song.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SongInfoDto {
    String id;
    String albumId;
    String albumName;
    String albumCoverUrl;
    String artistName;
    String reviewCount;
    String averageRating;
    Long likeCount;
    Boolean isLiked;
    Long myRating;
}
