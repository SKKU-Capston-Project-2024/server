package site.mutopia.server.spotify;

import com.neovisionaries.i18n.CountryCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Component;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Album;
import se.michaelthelin.spotify.model_objects.specification.AlbumSimplified;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchAlbumsRequest;
import site.mutopia.server.domain.album.domain.MutopiaAlbum;
import site.mutopia.server.domain.album.repository.AlbumRepository;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class SpotifyAlbumRepository {

    private final SpotifyApi spotifyApi;


    public Album findAlbumById(String albumId) {

        try {
            Album album =
                    spotifyApi.getAlbum(albumId).build().execute();
            return album;
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Album> findAlbumByAlbumName(String albumName) {
        return null;
    }

    public List<AlbumSimplified> findAlbumByArtistName(String artistName, int limit, int offset) {
        try {
            String query = "artist:" + artistName;
            SearchAlbumsRequest build = spotifyApi.searchAlbums(query).build();
            AlbumSimplified[] albums = build.execute().getItems();
            log.info("albums {}", albums);

            return Arrays.asList(albums);
        } catch (Exception e) {
            log.error("error", e);
            return null;
        }
    }

    public MutopiaAlbum findAlbumByArtistNameOrAlbumName(String keyword) {
        return null;
    }

    public List<AlbumSimplified> findAlbumByKeyword(String keyword, int limit, int offset) {
        try {
            SearchAlbumsRequest build =
                    spotifyApi.searchAlbums(keyword).market(CountryCode.KR).build();
            AlbumSimplified[] albums = build.execute().getItems();
            return Arrays.asList(albums);
        } catch (Exception e) {
            log.error("error", e);
            return null;
        }
    }
}