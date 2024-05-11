package site.mutopia.server.domain.album.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Formula;
import site.mutopia.server.domain.song.entity.SongEntity;

import java.util.List;

@Builder
@Getter
@Setter
@Entity
@Table(name = "album")
@AllArgsConstructor
@NoArgsConstructor
public class AlbumEntity {

    @Id
    String id;

    @Column
    String name;

    @Column
    String artistName;

    @Column
    String coverImageUrl;

    @Column
    String releaseDate;

    @Column
    Long length;

    @OneToMany
    @JoinColumn(name = "album_id")
    List<SongEntity> songs;

    @Formula("(select count(*) from album_review r where r.album_id = id)")
    @Basic(fetch = FetchType.LAZY)
    Long totalReviewCount;

    @Formula("(select avg(r.rating) from album_rating r where r.album_id = id)")
    @Basic(fetch = FetchType.LAZY)
    Double averageRating;

    @Formula("(select count(*) from album_like l where l.album_id = id)")
    Long totalLikeCount;
}