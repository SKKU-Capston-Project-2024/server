package site.mutopia.server.domain.albumRating.entity;

import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode
public class AlbumRatingId implements Serializable {
    private Long writer;
    private Long album;
}
