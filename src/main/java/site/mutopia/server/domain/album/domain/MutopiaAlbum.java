package site.mutopia.server.domain.album.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import site.mutopia.server.domain.album.dto.AlbumTrackDto;
import site.mutopia.server.domain.album.dto.RatingDistributionDto;
import site.mutopia.server.domain.album.dto.ReviewPreviewDto;

import java.util.List;

@RequiredArgsConstructor
@Getter
@Setter
public class MutopiaAlbum {
    String albumId;
    String albumName;
    String artistName;
    String albumImg;
    String releaseDate;
    String albumLength;
    Long totalReviewCount;
    List<ReviewPreviewDto> albumReviewList;
    List<AlbumTrackDto> albumTrackList;
    Long averageRating;
    Long yourRating;
    Long totalRatingCount;
    Long likeCount;
    List<ReviewPreviewDto> reviewPreviews;
    List<RatingDistributionDto> starDistribution;


}