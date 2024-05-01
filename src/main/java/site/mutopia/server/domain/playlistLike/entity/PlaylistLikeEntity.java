package site.mutopia.server.domain.playlistLike.entity;

import jakarta.persistence.*;
import site.mutopia.server.domain.playlist.entity.PlaylistEntity;
import site.mutopia.server.domain.user.entity.UserEntity;

@Entity
@Table(name = "playlist_like")
@IdClass(PlaylistLikeId.class)
public class PlaylistLikeEntity {
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Id
    @ManyToOne
    @JoinColumn(name = "playlist_id")
    private PlaylistEntity playlist;
}