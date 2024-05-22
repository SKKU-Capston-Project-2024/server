package site.mutopia.server.domain.songComment.dto;

import lombok.Data;

@Data
public class SongCommentReqDto {
    private Integer rating;
    private String comment;
}
