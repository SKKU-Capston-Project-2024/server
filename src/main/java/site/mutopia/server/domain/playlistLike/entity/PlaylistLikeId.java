package site.mutopia.server.domain.playlistLike.entity;

import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode
public class PlaylistLikeId implements Serializable {
    private String user;
    private Long playlist;
}