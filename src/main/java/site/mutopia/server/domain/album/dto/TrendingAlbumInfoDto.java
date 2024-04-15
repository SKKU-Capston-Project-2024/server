package site.mutopia.server.domain.album.dto;

public record TrendingAlbumInfoDto(
        String albumId,
        String albumName,
        String albumImg,
        String artistName
) {
}
