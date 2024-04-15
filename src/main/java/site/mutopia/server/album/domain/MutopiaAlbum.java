package site.mutopia.server.album.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class MutopiaAlbum {
    String id;
    String name;
    String artistName;
    String coverImageUrl;
    String releaseDate;
    Long length;
}