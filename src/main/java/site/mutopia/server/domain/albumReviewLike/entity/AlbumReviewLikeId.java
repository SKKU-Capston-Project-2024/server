package site.mutopia.server.domain.albumReviewLike.entity;

import lombok.EqualsAndHashCode;
import java.io.Serializable;

@EqualsAndHashCode
public class AlbumReviewLikeId implements Serializable {
    private Long user;
    private Long review;
}
