package site.mutopia.server.domain.playlistSong.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.mutopia.server.domain.playlist.entity.PlaylistEntity;
import site.mutopia.server.domain.song.entity.SongEntity;

@Entity
@Table(name = "playlist_song")
@IdClass(PlaylistSongId.class)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlaylistSongEntity {
    @Id
    @ManyToOne
    @JoinColumn(name = "playlist_id")
    private PlaylistEntity playlist;

    @Id
    @ManyToOne
    @JoinColumn(name = "song_id")
    private SongEntity song;

    @Column(name = "list_track_order")
    private Integer listTrackOrder;
}