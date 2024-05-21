package site.mutopia.server.domain.playlist.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PlaylistSaveReqDto {

    private String title;

    private String content;
}
