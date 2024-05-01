package site.mutopia.server.domain.albumRating.entity;

import jakarta.persistence.*;
import site.mutopia.server.domain.album.entity.AlbumEntity;
import site.mutopia.server.domain.user.entity.UserEntity;

@Entity
@Table(name = "album_rating")
@IdClass(AlbumRatingId.class)
public class AlbumRatingEntity {
    @Id
    @ManyToOne
    @JoinColumn(name = "writer_id")
    private UserEntity writer;

    @Id
    @ManyToOne
    @JoinColumn(name = "album_id")
    private AlbumEntity album;

    @Column(name = "rating")
    private Integer rating;
}