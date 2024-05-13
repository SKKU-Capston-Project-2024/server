package site.mutopia.server.spotify.convertor;

import site.mutopia.server.domain.album.entity.AlbumEntity;
import site.mutopia.server.domain.song.entity.SongEntity;
import site.mutopia.server.spotify.dto.item.Item;
import site.mutopia.server.spotify.dto.item.Track;


public class DomainConvertor {
    public static AlbumEntity toDomain(Item item){
        return AlbumEntity.builder()
                .id(item.id)
                .name(item.name)
                .artistName(item.artists.get(0).name)
                .coverImageUrl(item.images.get(0).url)
                .releaseDate(item.release_date)
                .length(null)
                .build();

    }

    public static SongEntity toDomain(Track track, String albumId) {
        return SongEntity.builder()
                .title(track.getName())
                .id(track.getId())
                .trackNumber(track.getTrack_number())
                .duration(track.getDuration_ms() / 1000)
                .build();
    }
}
