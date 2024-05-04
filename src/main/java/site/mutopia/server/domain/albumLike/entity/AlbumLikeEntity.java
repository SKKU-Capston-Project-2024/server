package site.mutopia.server.domain.albumLike.entity;

import jakarta.persistence.*;
import site.mutopia.server.domain.album.entity.AlbumEntity;
import site.mutopia.server.domain.user.entity.UserEntity;

@Entity
@Table(name = "album_like")
@IdClass(AlbumLikeEntityId.class)
public class AlbumLikeEntity {
    @Id
    @JoinColumn(name = "album_id")
    @ManyToOne
    private AlbumEntity albumId;

    @Id
    @JoinColumn(name = "user_id")
    @ManyToOne
    private UserEntity userId;
}
