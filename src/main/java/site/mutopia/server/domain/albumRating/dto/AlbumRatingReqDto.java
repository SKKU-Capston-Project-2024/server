package site.mutopia.server.domain.albumRating.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AlbumRatingReqDto {
    private Integer rating;
}