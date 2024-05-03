package site.mutopia.server.domain.albumLike.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.io.Serializable;

@Entity
public class AlbumLikeEntityId implements Serializable {
    @Id
    private String albumId;
    @Id
    private String userId;
}
