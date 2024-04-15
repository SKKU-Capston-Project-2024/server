package site.mutopia.server.album.dto.response;

import site.mutopia.server.album.dto.AlbumTrackDto;
import site.mutopia.server.album.dto.RatingDistributionDto;
import site.mutopia.server.album.dto.ReviewPreviewDto;

import java.util.List;

public record AlbumDetailResDto(
        String albumId,
        String albumName,
        String artistName,
        String albumImg,
        String releaseDate,
        String albumLength,
        Long totalReviewCount,

        List<ReviewPreviewDto> albumReviewList,

        List<AlbumTrackDto> albumTrackList,

        Long averageRating,

        Long yourRating,

        Long totalRatingCount,

        Long likeCount,

        List<ReviewPreviewDto> reviewPreviews,

        List<RatingDistributionDto> starDistribution

) {
}
