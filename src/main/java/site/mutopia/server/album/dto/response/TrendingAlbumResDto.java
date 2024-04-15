package site.mutopia.server.album.dto.response;

import site.mutopia.server.album.dto.TrendingAlbumInfoDto;

import java.util.List;

public record TrendingAlbumResDto(
        List<TrendingAlbumInfoDto> albumList
) {
    /*
    public TrendingAlbumResDto {
        // TODO: Entity To Dto Mapper Logic
    }*/
}
