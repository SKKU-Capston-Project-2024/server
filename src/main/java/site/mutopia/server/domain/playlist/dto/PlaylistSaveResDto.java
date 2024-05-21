package site.mutopia.server.domain.playlist.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PlaylistSaveResDto {
    private Long playlistId;
}
