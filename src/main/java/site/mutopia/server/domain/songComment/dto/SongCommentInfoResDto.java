package site.mutopia.server.domain.songComment.dto;

import lombok.*;
import site.mutopia.server.domain.songComment.entity.SongCommentEntity;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static site.mutopia.server.global.util.StringUtil.unixTimeToString;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SongCommentInfoResDto {

    private CommentWriterInfo writer;
    private SongCommentInfo songComment;

    @Getter
    @AllArgsConstructor
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CommentWriterInfo {
        private String userId;
        private String username;
        private String profileImageUrl;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class SongInfo {
        private String id;
        private String title;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class SongCommentInfo {
        private SongInfo songInfo;
        private Integer rating;
        private String comment;
        private String createdAt;
        private Boolean isLiked;
    }

    public static SongCommentInfoResDto fromEntity(SongCommentEntity entity) {
        SongInfo songInfo = SongInfo.builder()
                .id(entity.getSong().getId())
                .title(entity.getSong().getTitle())
                .build();

        CommentWriterInfo writerInfo = CommentWriterInfo.builder()
                .userId(entity.getWriter().getId())
                .username(entity.getWriter().getUsername())
                .profileImageUrl(entity.getWriter().getProfile().getProfilePicUrl())
                .build();

        SongCommentInfo songCommentInfo = SongCommentInfo.builder()
                .songInfo(songInfo)
                .rating(entity.getRating())
                .comment(entity.getComment())
                .createdAt(unixTimeToString(entity.getCreatedAt()))
                .build();


        return SongCommentInfoResDto.builder()
                .writer(writerInfo)
                .songComment(songCommentInfo)
                .build();
    }

    public SongCommentInfoResDto(
            String writerId, String writerName, String writerProfileImageUrl,
            String songId, String songTitle, Integer rating, String comment,
            Long createdAt, Boolean isLiked
    ) {

        CommentWriterInfo writerInfo = CommentWriterInfo.builder()
                .userId(writerId)
                .username(writerName)
                .profileImageUrl(writerProfileImageUrl)
                .build();

        SongCommentInfo songCommentInfo = SongCommentInfo.builder()
                .songInfo(SongInfo.builder()
                        .id(songId)
                        .title(songTitle)
                        .build())
                .rating(rating)
                .comment(comment)
                .createdAt(unixTimeToString(createdAt))
                .isLiked(isLiked)
                .build();

        this.writer = writerInfo;
        this.songComment = songCommentInfo;


    }



}
