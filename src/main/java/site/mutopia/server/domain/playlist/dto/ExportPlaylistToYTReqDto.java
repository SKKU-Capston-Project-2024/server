package site.mutopia.server.domain.playlist.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExportPlaylistToYTReqDto {

    private String title;

    private String description;

}
