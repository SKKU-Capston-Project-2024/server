package site.mutopia.server.domain.album.dto.response;

import site.mutopia.server.domain.album.dto.TrendingAlbumInfoDto;

import java.util.List;

public record TrendingAlbumResDto(
        List<TrendingAlbumInfoDto> albumList
) {
    /*
    public TrendingAlbumResDto {
        // TODO: Entity To Dto Mapper Logic
    }*/
}
