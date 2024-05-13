package site.mutopia.server.domain.album.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.mutopia.server.domain.song.entity.SongEntity;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AlbumTrackDto
{
    String id;
    String name;
    Integer trackNumber;
    Integer rating;
    String length;

    public AlbumTrackDto(SongEntity songEntity) {
        this.id = songEntity.getId();
        this.name = songEntity.getTitle();
        this.trackNumber = songEntity.getTrackNumber();
        this.rating = null;
        this.length = String.format("%d:%02d", songEntity.getDuration() / 60, songEntity.getDuration() % 60);
    }
}
