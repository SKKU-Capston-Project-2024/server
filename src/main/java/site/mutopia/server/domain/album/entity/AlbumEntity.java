package site.mutopia.server.domain.album.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;

@Builder
@Getter
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

    @Formula("(select count(*) from album_review r where r.album_id = id)")
    @Basic(fetch = FetchType.LAZY)
    Long totalReviewCount;

    @Formula("(select avg(r.rating) from album_rating r where r.album_id = id)")
    @Basic(fetch = FetchType.LAZY)
    Double averageRating;

    @Formula("(select count(*) from album_like l where l.album_id = id)")
    Long totalLikeCount;
}