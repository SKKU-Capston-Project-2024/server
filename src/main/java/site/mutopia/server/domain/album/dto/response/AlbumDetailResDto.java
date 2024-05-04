package site.mutopia.server.domain.album.dto.response;

import lombok.Builder;
import lombok.Data;
import site.mutopia.server.domain.album.dto.AlbumTrackDto;
import site.mutopia.server.domain.album.dto.RatingDistributionDto;
import site.mutopia.server.domain.album.dto.ReviewPreviewDto;
import site.mutopia.server.domain.album.entity.AlbumEntity;

import java.util.List;

@Builder
@Data
public class AlbumDetailResDto {
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

    Long likeCount;

    public static AlbumDetailResDto toDto(AlbumEntity entity){
        return AlbumDetailResDto.builder()
                .albumId(entity.getId())
                .albumName(entity.getName())
                .artistName(entity.getArtistName())
                .albumImg(entity.getCoverImageUrl())
                .releaseDate(entity.getReleaseDate())
                .albumLength(null)
                .totalReviewCount(entity.getTotalReviewCount())
                .averageRating(entity.getAverageRating())
                .likeCount(entity.getTotalLikeCount())
                .build();

    }

}