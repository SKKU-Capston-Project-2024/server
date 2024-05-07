package site.mutopia.server.domain.albumLike.entity;

import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode
public class AlbumLikeEntityId implements Serializable {
    private String album;
    private String user;
}