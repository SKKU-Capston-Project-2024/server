package site.mutopia.server.spotify;

import se.michaelthelin.spotify.model_objects.specification.AlbumSimplified;
import se.michaelthelin.spotify.model_objects.specification.ArtistSimplified;
import se.michaelthelin.spotify.model_objects.specification.Image;
import site.mutopia.server.album.domain.MutopiaAlbum;

import java.util.Arrays;

public class DomainConvertor {

    public static MutopiaAlbum toMutopia(AlbumSimplified album) {

        Image[] images = album.getImages();
        String imageUrl = images.length > 0 ? images[0].getUrl() : null;

        String artistName = String.join(", ",
                Arrays.stream(album.getArtists()).map(ArtistSimplified::getName).toArray(String[]::new));

        return MutopiaAlbum.builder()
                .id(album.getId())
                .name(album.getName())
                .artistName(artistName)
                .coverImageUrl(imageUrl)
                .build();

    }

}
