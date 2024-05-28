package site.mutopia.server.domain.songCommentLike.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import site.mutopia.server.domain.songCommentLike.entity.SongCommentLikeEntity;
import site.mutopia.server.global.util.StringUtil;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class LikeSongCommentDto {
    String songId;
    String albumId;
    String albumCoverImg;
    String songTitle;
    String artistName;
    String albumName;
    String writerId;
    String writerName;
    String writerProfileImg;
    String comment;
    Boolean isLiked;
    Integer rating;
    String createdAt;

    public LikeSongCommentDto(String songId, String albumId, String albumCoverImg, String songTitle, String artistName, String albumName, String writerId, String writerName, String writerProfileImg, String comment, Boolean isLiked, Integer rating, Long createdAt) {
        this.songId = songId;
        this.albumId = albumId;
        this.albumCoverImg = albumCoverImg;
        this.songTitle = songTitle;
        this.artistName = artistName;
        this.albumName = albumName;
        this.writerId = writerId;
        this.writerName = writerName;
        this.writerProfileImg = writerProfileImg;
        this.comment = comment;
        this.isLiked = isLiked;
        this.rating = rating;
        this.createdAt = StringUtil.unixTimeToString(createdAt);
    }
}
