package site.mutopia.server.domain.youtubePlaylist.entity;

import jakarta.persistence.*;
import lombok.*;
import site.mutopia.server.domain.user.entity.UserEntity;

@Entity
@Table(name = "youtube_playlist")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class YoutubePlaylistEntity {

    @Id @Column(name = "youtube_playlist_id")
    private String id;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private UserEntity creator;
}
