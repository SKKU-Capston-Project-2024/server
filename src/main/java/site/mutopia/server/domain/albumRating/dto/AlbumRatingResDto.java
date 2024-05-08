package site.mutopia.server.domain.albumRating.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AlbumRatingResDto {
    private Integer rating;
}