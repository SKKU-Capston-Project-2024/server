package site.mutopia.server.domain.albumReview.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AlbumReviewCheckResDto {
    private Long albumReviewId;
    private Boolean userHasReviewed;
}
