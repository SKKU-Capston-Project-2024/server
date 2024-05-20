package site.mutopia.server.domain.playlist.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AddSongToPlaylistResDto {

    private Long playlistId;

    private String songId;

    private Integer listTrackOrder;
}
