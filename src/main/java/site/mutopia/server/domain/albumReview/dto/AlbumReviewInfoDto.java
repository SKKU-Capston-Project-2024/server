package site.mutopia.server.domain.albumReview.dto;

import lombok.*;


@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AlbumReviewInfoDto {

    private ReviewInfoDto review;
    private WriterInfoDto writer;
    private AlbumInfoDto album;

    // Constructor used by JPQL to create instances
    public AlbumReviewInfoDto(Long reviewId, String title, String content, Integer rating, String albumId,
                              String writerId, String username,
                              String name, String artistName, String coverImageUrl, String releaseDate,
                              Long length, Long totalReviewCount, Double averageRating, Long totalLikeCount) {
        this.review = new ReviewInfoDto(reviewId,title, content, rating, false);
        this.writer = new WriterInfoDto(writerId, username);
        this.album = new AlbumInfoDto(albumId, name, artistName, coverImageUrl, releaseDate,
                length, totalReviewCount, averageRating, totalLikeCount);
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


