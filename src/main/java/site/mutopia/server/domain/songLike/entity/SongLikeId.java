package site.mutopia.server.domain.songLike.entity;

import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode
public class SongLikeId implements Serializable {
    private String song;
    private String user;
}
