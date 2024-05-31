package site.mutopia.server.domain.album.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.mutopia.server.domain.album.dto.AlbumTrackDto;
import site.mutopia.server.domain.album.dto.ReviewPreviewDto;
import site.mutopia.server.domain.album.entity.AlbumEntity;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlbumDetailResDto {
    String albumId;
    String albumName;
    String artistName;
    String albumImg;
    String releaseDate;
    Long albumLength;
    Long totalReviewCount;

    List<ReviewPreviewDto> albumReviewList;

    List<AlbumTrackDto> albumTrackList;

    Double averageRating;

    Long likeCount;

    public static AlbumDetailResDto toDto(AlbumEntity entity){
        return AlbumDetailResDto.builder()
                .albumLength(entity.getLength())
                .albumId(entity.getId())
                .albumName(entity.getName())
                .artistName(entity.getArtistName())
                .albumImg(entity.getCoverImageUrl())
                .releaseDate(entity.getReleaseDate())
                .totalReviewCount(entity.getTotalReviewCount())
                .likeCount(entity.getTotalLikeCount())
                .averageRating(entity.getAverageRating())
                .albumTrackList(entity.getSongs().stream().map(AlbumTrackDto::new).collect(Collectors.toList()))
                .build();
    }

}