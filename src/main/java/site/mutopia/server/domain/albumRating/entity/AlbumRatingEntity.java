package site.mutopia.server.domain.albumRating.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import site.mutopia.server.domain.album.entity.AlbumEntity;
import site.mutopia.server.domain.user.entity.UserEntity;

@Entity
@Table(name = "album_rating")
@IdClass(AlbumRatingId.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class AlbumRatingEntity {
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", columnDefinition = "VARCHAR(255)")
    @MapsId("user")
    private UserEntity user;

    @Id
    @ManyToOne
    @JoinColumn(name = "album_id", referencedColumnName = "id", columnDefinition = "VARCHAR(255)")
    @MapsId("album")
    private AlbumEntity album;

    @Column(name = "rating")
    private Integer rating;

    public void modifyRating(Integer rating) {
        this.rating = rating;
    }
}