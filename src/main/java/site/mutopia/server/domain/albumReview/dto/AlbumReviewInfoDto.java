package site.mutopia.server.domain.albumReview.dto;

import lombok.*;
import site.mutopia.server.domain.albumReview.entity.AlbumReviewEntity;


@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AlbumReviewInfoDto {

    private ReviewInfoDto review;
    private WriterInfoDto writer;
    private AlbumInfoDto album;

    // Constructor used by JPQL to create instances
    public AlbumReviewInfoDto(Long reviewId, String title, String content, Integer rating, String albumId,
                              String writerId, String username, String userProfileImageUrl,
                              String name, String artistName, String coverImageUrl, String releaseDate,
                              Long length, Long totalReviewCount, Double averageRating, Long totalLikeCount) {
        this.review = new ReviewInfoDto(reviewId,title, content, rating, false);
        this.writer = new WriterInfoDto(writerId, username, userProfileImageUrl);
        this.album = new AlbumInfoDto(albumId, name, artistName, coverImageUrl, releaseDate,
                length, totalReviewCount, averageRating, totalLikeCount);
    }

    public static AlbumReviewInfoDto fromEntity(AlbumReviewEntity entity){
        return new AlbumReviewInfoDto(
                entity.getId(),
                entity.getTitle(),
                entity.getContent(),
                entity.getRating(),
                entity.getAlbum().getId(),
                entity.getWriter().getId(),
                entity.getWriter().getUsername(),
                entity.getWriter().getProfile().getProfilePicUrl(),
                entity.getAlbum().getName(),
                entity.getAlbum().getArtistName(),
                entity.getAlbum().getCoverImageUrl(),
                entity.getAlbum().getReleaseDate(),
                entity.getAlbum().getLength(),
                entity.getAlbum().getTotalReviewCount(),
                entity.getAlbum().getAverageRating(),
                entity.getAlbum().getTotalLikeCount()
        );

    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class ReviewInfoDto {
        private Long id;
        private String title;
        private String content;
        private Integer rating;
        @Setter
        private Boolean isLiked;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class WriterInfoDto {
        private String id;
        private String username;
        private String profileImageUrl;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class AlbumInfoDto {
        private String id;
        private String name;
        private String artistName;
        private String coverImageUrl;
        private String releaseDate;
        private Long length;
        private Long totalReviewCount;
        private Double averageRating;
        private Long totalLikeCount;
    }
}


