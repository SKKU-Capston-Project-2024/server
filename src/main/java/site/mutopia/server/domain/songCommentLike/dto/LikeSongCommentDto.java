package site.mutopia.server.domain.songCommentLike.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import site.mutopia.server.domain.songCommentLike.entity.SongCommentLikeEntity;

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


    public static LikeSongCommentDto of(SongCommentLikeEntity entity){
        return new LikeSongCommentDto(
                entity.getSongComment().getSong().getId(),
                entity.getSongComment().getSong().getAlbum().getId(),
                entity.getSongComment().getSong().getAlbum().getCoverImageUrl(),
                entity.getSongComment().getSong().getTitle(),
                entity.getSongComment().getSong().getAlbum().getArtistName(),
                entity.getSongComment().getSong().getAlbum().getName(),
                entity.getSongComment().getWriter().getId(),
                entity.getSongComment().getWriter().getUsername(),
                entity.getSongComment().getWriter().getProfile().getProfilePicUrl(),
                entity.getSongComment().getComment(),
                false
        );
    }
}
