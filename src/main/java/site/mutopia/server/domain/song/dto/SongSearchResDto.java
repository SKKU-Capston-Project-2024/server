package site.mutopia.server.domain.song.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import site.mutopia.server.domain.song.entity.SongEntity;

@Getter
@AllArgsConstructor
public class SongSearchResDto {
    String id;
    String name;
    String albumCoverUrl;
    String artistName;

    public SongSearchResDto(SongEntity songEntity, String albumCoverUrl, String artistName) {
        this.id = songEntity.getSongId();
        this.name = songEntity.getTitle();
        this.albumCoverUrl = albumCoverUrl;
        this.artistName = artistName;
    }
}
