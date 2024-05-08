package site.mutopia.server.domain.albumRating.entity;

import lombok.*;

import java.io.Serializable;

@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class AlbumRatingId implements Serializable {
    private String user;
    private String album;
}
