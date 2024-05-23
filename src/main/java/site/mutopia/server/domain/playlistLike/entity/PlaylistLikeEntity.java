package site.mutopia.server.domain.playlistLike.entity;

import jakarta.persistence.*;
import lombok.*;
import site.mutopia.server.domain.playlist.entity.PlaylistEntity;
import site.mutopia.server.domain.user.entity.UserEntity;

@Entity
@Table(name = "playlist_like")
@IdClass(PlaylistLikeId.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class PlaylistLikeEntity {
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    @MapsId("user")
    private UserEntity user;

    @Id
    @ManyToOne
    @JoinColumn(name = "playlist_id")
    @MapsId("playlist")
    private PlaylistEntity playlist;
}