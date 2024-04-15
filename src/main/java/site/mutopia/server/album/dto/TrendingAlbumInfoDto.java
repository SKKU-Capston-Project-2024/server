package site.mutopia.server.album.dto;

public record TrendingAlbumInfoDto(
        String albumId,
        String albumName,
        String albumImg,
        String artistName
) {
}
