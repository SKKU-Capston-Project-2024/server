package site.mutopia.server.spotify.convertor;

import site.mutopia.server.domain.album.entity.AlbumEntity;
import site.mutopia.server.spotify.dto.item.Item;

public class DomainConvertor {
    public static AlbumEntity toDomain(Item item){
        return AlbumEntity.builder()
                .id(item.id)
                .name(item.name)
                .artistName(item.artists.get(0).name)
                .coverImageUrl(item.images.get(0).url)
                .releaseDate(item.release_date)
                .length(0L)
                .build();

    }
}
