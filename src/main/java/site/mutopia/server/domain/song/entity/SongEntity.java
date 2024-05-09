package site.mutopia.server.domain.song.entity;

import jakarta.persistence.*;
import lombok.*;
import site.mutopia.server.domain.album.entity.AlbumEntity;
import java.time.LocalDate;

@Entity
@Builder
@Data
@AllArgsConstructor
@Table(name = "song")
public class SongEntity {
    @Id
    @Column(name = "song_id")
    private String songId;

    @Column(name = "title")
    private String title;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Column(name = "album_id")
    private String albumId;

    public SongEntity() {

    }
}