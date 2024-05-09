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

    @JoinColumn(name = "album_id")
    @ManyToOne
    private AlbumEntity album;

    @Column(name = "track_number")
    private Integer trackNumber;

    public SongEntity() {

    }
}