package site.mutopia.server.domain.playlist.dto;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ExportPlaylistToSpotifyResDto {
    private String url;
}
