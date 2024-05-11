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
    private List<SongBriefInfo> songs;

    @Getter
    @AllArgsConstructor
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class SongBriefInfo {
        private String songId;
        private String title;
        private Integer trackOrder;
    }

    public PlaylistInfoDto(Long playlistId, String creatorId, Long likeCount) {
        this.playlistId = playlistId;
        this.creatorId = creatorId;
        this.likeCount = likeCount;
    }
}
