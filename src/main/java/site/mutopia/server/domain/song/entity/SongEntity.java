package site.mutopia.server.domain.song.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "song")
public class SongEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "song_id")
    private Integer songId;

    @Column(name = "title")
    private String title;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    // TODO: set relationships with other entities (album)
}