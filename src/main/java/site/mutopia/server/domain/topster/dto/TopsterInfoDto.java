package site.mutopia.server.domain.topster.dto;

import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class TopsterInfoDto {

    private TopsterInfoDetailDto topster;
    private UserInfoDto user;
    private List<TopsterAlbumInfoDto> topsterAlbums;

    @Getter
    @AllArgsConstructor
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class TopsterInfoDetailDto {
        private Long id;
        private String title;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class UserInfoDto {
        private String id;
        private String username;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class TopsterAlbumInfoDto {
        private String id;
        private String name;
        private String artistName;
        private String coverImageUrl;
        private String releaseDate;
        private Long length;
    }
}
