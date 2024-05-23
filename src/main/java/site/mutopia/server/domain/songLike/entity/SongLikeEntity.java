package site.mutopia.server.domain.songLike.entity;

import jakarta.persistence.*;
import lombok.*;
import site.mutopia.server.domain.song.entity.SongEntity;
import site.mutopia.server.domain.songLike.entity.SongLikeId;
import site.mutopia.server.domain.user.entity.UserEntity;

@Entity
@Table(name = "song_like")
@IdClass(SongLikeId.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class SongLikeEntity {
    @Id
    @ManyToOne
    @JoinColumn(name = "song_id")
    @MapsId("song")
    private SongEntity song;

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    @MapsId("user")
    private UserEntity user;
}
