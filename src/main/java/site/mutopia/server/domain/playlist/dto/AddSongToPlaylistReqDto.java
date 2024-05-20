package site.mutopia.server.domain.playlist.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AddSongToPlaylistReqDto {

    private String songId;

    private Integer trackOrder;
}
