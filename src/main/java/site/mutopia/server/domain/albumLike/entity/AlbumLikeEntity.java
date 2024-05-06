package site.mutopia.server.domain.albumLike.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import site.mutopia.server.domain.album.entity.AlbumEntity;
import site.mutopia.server.domain.user.entity.UserEntity;

@Entity
@Table(name = "album_like")
@IdClass(AlbumLikeEntityId.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class AlbumLikeEntity {
    @Id
    @ManyToOne
    @JoinColumn(name = "album_id")
    @MapsId("albumId")
    private AlbumEntity albumId;

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    @MapsId("userId")
    private UserEntity userId;
}
