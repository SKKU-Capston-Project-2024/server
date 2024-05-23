package site.mutopia.server.domain.albumLike.entity;

import jakarta.persistence.*;
import lombok.*;
import site.mutopia.server.domain.album.entity.AlbumEntity;
import site.mutopia.server.domain.user.entity.UserEntity;

@Entity
@Table(name = "album_like")
@IdClass(AlbumLikeId.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class AlbumLikeEntity {
    @Id
    @ManyToOne
    @JoinColumn(name = "album_id")
    @MapsId("album")
    private AlbumEntity album;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @MapsId("user")
    private UserEntity user;
}
