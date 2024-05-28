package site.mutopia.server.domain.playlist.dto;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ExportPlaylistToSpotifyReqDto {
    private PlaylistDto playlist;

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Builder
    public static class PlaylistDto {
        private String name;
        private String description;
    }
}
