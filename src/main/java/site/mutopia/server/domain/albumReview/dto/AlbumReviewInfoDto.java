package site.mutopia.server.domain.albumReview.dto;

import lombok.*;
import site.mutopia.server.domain.albumReview.entity.AlbumReviewEntity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static site.mutopia.server.global.util.StringUtil.unixTimeToString;


@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AlbumReviewInfoDto {

    private ReviewInfoDto review;
    private WriterInfoDto writer;
    private AlbumInfoDto album;

    // Constructor used by JPQL to create instances
    @Builder
    public AlbumReviewInfoDto(Long reviewId, String title, String content, Integer rating, Long likeCount, Long createdAt, String albumId,
                              String writerId, String username, String userProfileImageUrl,
                              String name, String artistName, String coverImageUrl, String releaseDate,
                              Long length, Long totalReviewCount, Long totalLikeCount, Boolean isLiked) {
        this.review = new ReviewInfoDto(reviewId,title, content, rating, isLiked, likeCount, unixTimeToString(createdAt));
        this.writer = new WriterInfoDto(writerId, username, userProfileImageUrl);
        this.album = new AlbumInfoDto(albumId, name, artistName, coverImageUrl, releaseDate,
                length, totalReviewCount, totalLikeCount);
    }

    public static AlbumReviewInfoDto fromEntity(AlbumReviewEntity entity){
        return new AlbumReviewInfoDto(
                entity.getId(),
                entity.getTitle(),
                entity.getContent(),
                entity.getRating(),
                entity.getLikeCount(),
                entity.getCreatedAt(),
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
                entity.getAlbum().getTotalLikeCount(),
                null
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
        private Boolean isLiked = false;
        @Setter
        private Long likeCount;
        @Setter
        private String createdAt;
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
        private Long totalLikeCount;
    }


}


