package site.mutopia.server.domain.playlistSong.entity;

import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode
public class PlaylistSongId implements Serializable {
    private Long playlist;
    private String song;
}
