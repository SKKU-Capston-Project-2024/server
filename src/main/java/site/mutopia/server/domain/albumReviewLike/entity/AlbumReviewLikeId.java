package site.mutopia.server.domain.albumReviewLike.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class AlbumReviewLikeId implements Serializable {
    private String user;
    private Long review;
}
