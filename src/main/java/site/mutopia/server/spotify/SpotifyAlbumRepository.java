package site.mutopia.server.spotify;

import lombok.RequiredArgsConstructor;
import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Component;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Album;
import site.mutopia.server.domain.album.domain.MutopiaAlbum;
import site.mutopia.server.domain.album.repository.AlbumRepository;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class SpotifyAlbumRepository implements AlbumRepository {

    private final SpotifyApi spotifyApi;

    @Override
    public MutopiaAlbum findAlbumById(String albumId) {
        try {
            Album album =
                    spotifyApi.getAlbum(albumId).build().execute();
            return new MutopiaAlbum();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public MutopiaAlbum findAlbumByAlbumName(String albumName) {
        return null;
    }

    @Override
    public MutopiaAlbum findAlbumByArtistName(String artistName) {
        return null;
    }

    @Override
    public MutopiaAlbum findAlbumByArtistNameOrAlbumName(String keyword) {
        return null;
    }
}