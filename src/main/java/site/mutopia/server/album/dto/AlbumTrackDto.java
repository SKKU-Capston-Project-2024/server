package site.mutopia.server.album.dto;

public record AlbumTrackDto(
        String id,
        String name,
        String order,
        Long rating,
        String length
) {
}
