package site.mutopia.server.domain.song.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;
import site.mutopia.server.domain.album.entity.AlbumEntity;
import java.time.LocalDate;

@Entity
@Builder
@Data
@AllArgsConstructor
@Table(name = "song")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SongEntity {
    @Id
    @Column(name = "song_id")
    private String id;

    @Column(name = "title")
    private String title;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @ManyToOne
    @JoinColumn(name = "album_id")
    private AlbumEntity album;

    @Column(name = "track_number")
    private Integer trackNumber;

    @Formula("(select avg(r.rating) from song_comment r where r.song_id = song_id)")
    private Double averageRating;

}