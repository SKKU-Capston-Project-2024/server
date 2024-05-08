package site.mutopia.server.domain.topsterAlbum.entity;

import lombok.EqualsAndHashCode;
import java.io.Serializable;

@EqualsAndHashCode
public class TopsterAlbumId implements Serializable {
    private Long topster;
    private String album;
}