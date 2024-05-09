package site.mutopia.server.domain.topster.dto;

import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TopsterAlbumDeleteReqDto {
    private List<String> albumIds;
}
