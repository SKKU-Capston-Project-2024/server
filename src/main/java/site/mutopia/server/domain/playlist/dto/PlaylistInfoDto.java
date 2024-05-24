package site.mutopia.server.domain.playlist.dto;

import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter
public class PlaylistInfoDto {
    private Long playlistId;
    private String creatorId;
    private Long likeCount;
    private String title;
    private String content;
    private List<SongBriefInfo> songs;

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

    public PlaylistInfoDto(Long playlistId, String creatorId, Long likeCount, String title, String content) {
        this.playlistId = playlistId;
        this.creatorId = creatorId;
        this.likeCount = likeCount;
        this.title = title;
        this.content = content;
    }
}
