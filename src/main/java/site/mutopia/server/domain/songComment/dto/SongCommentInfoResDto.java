package site.mutopia.server.domain.songComment.dto;

import lombok.*;

import java.time.LocalDate;

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
    }

    @Getter
    @AllArgsConstructor
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class SongInfo {
        private Long id;
        private String title;
        private Integer duration;
        private LocalDate releaseDate;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class SongCommentInfo {
        private SongInfo songInfo;
        private Integer rating;
        private String comment;
    }
}
