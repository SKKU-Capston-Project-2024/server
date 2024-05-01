package site.mutopia.server.domain.playlist.entity;

import jakarta.persistence.*;
import site.mutopia.server.domain.user.entity.UserEntity;

@Entity
@Table(name = "playlist")
public class PlaylistEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "playlist_id")
    private Long playlistId;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private UserEntity creator;

    @Column(name = "like_count")
    private Integer likeCount;
}