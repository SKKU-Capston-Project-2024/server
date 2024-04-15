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
public class SpotifyAlbumRepository implements AlbumRepository {

    private final SpotifyApi spotifyApi;

    @Override
    public MutopiaAlbum findAlbumById(String albumId) {
        try {
            Album album =
                    spotifyApi.getAlbum(albumId).build().execute();
            return null;
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public MutopiaAlbum findAlbumByAlbumName(String albumName) {
        return null;
    }

    @Override
    public List<MutopiaAlbum> findAlbumByArtistName(String artistName, int limit, int offset) {
        try {
            String query = "artist:" + artistName;
            SearchAlbumsRequest build = spotifyApi.searchAlbums(query).build();
            AlbumSimplified[] albums = build.execute().getItems();
            log.info("albums {}", albums);

            List<MutopiaAlbum> list = Arrays.stream(albums).map(DomainConvertor::toMutopia).toList();
            return list;
        } catch (Exception e) {
            log.error("error", e);
            return null;
        }
    }

    @Override
    public MutopiaAlbum findAlbumByArtistNameOrAlbumName(String keyword) {
        return null;
    }

    @Override
    public List<MutopiaAlbum> findAlbumByKeyword(String keyword, int limit, int offset) {
        try {
            SearchAlbumsRequest build =
                    spotifyApi.searchAlbums(keyword).market(CountryCode.KR).build();
            AlbumSimplified[] albums = build.execute().getItems();
            List<MutopiaAlbum> list = Arrays.stream(albums).map(DomainConvertor::toMutopia).toList();
            return list;
        } catch (Exception e) {
            log.error("error", e);
            return null;
        }
    }
}