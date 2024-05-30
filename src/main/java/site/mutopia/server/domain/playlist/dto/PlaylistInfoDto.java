package site.mutopia.server.domain.playlist.dto;

import lombok.*;

import java.util.List;

import static site.mutopia.server.global.util.StringUtil.unixTimeToString;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter
public class PlaylistInfoDto {
    private Long playlistId;
    private String creatorId;
    private String creatorName;
    private Long likeCount;
    private Boolean isLiked;
    private String title;
    private String content;
    private List<SongBriefInfo> songs;
    private String createdAt;

    @Getter
    @AllArgsConstructor
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class SongBriefInfo {
        private String songId;
        private String title;
        private Integer trackOrder;
        private String artistName;
        private String albumName;
        private String albumImgUrl;
    }

    public PlaylistInfoDto(Long playlistId, String creatorId, String creatorName, Long likeCount, Boolean isLiked, String title, String content, Long createdAt) {
        this.playlistId = playlistId;
        this.creatorId = creatorId;
        this.creatorName = creatorName;
        this.likeCount = likeCount;
        this.isLiked = isLiked;
        this.title = title;
        this.content = content;
        this.createdAt = unixTimeToString(createdAt);
    }
}
