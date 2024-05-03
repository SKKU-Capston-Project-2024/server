package site.mutopia.server.domain.album.repository;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import se.michaelthelin.spotify.model_objects.specification.Album;
import se.michaelthelin.spotify.model_objects.specification.AlbumSimplified;
import site.mutopia.server.domain.album.domain.MutopiaAlbum;
import site.mutopia.server.spotify.DomainConvertor;
import site.mutopia.server.spotify.SpotifyAlbumRepository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional
public class AlbumRepositoryImpl implements AlbumRepository{

    private final SpotifyAlbumRepository spotifyAlbumRepository;
    private final MutopiaAlbumRepository mutopiaAlbumRepository;

    @Override
    public MutopiaAlbum findAlbumById(String albumId) {
        return mutopiaAlbumRepository.findById(albumId).orElseThrow();
    }

    @Override
    public List<MutopiaAlbum> findAlbumByAlbumName(String albumName) {
        List<Album> spotifyAlbums = spotifyAlbumRepository.findAlbumByAlbumName(albumName);
        List<MutopiaAlbum> albums = spotifyAlbums.stream().map(DomainConvertor::toMutopia).toList();

        mutopiaAlbumRepository.saveAll(albums);

        return albums;
    }

    @Override
    public List<MutopiaAlbum> findAlbumByArtistName(String artistName, int limit, int offset) {
        List<AlbumSimplified> spotifyAlbums = spotifyAlbumRepository.findAlbumByArtistName(artistName, limit, offset);
        List<MutopiaAlbum> albums = spotifyAlbums.stream().map(DomainConvertor::toMutopia).toList();

        mutopiaAlbumRepository.saveAll(albums);

        return albums;
    }

    @Override
    public List<MutopiaAlbum> findAlbumsByArtistNameOrAlbumName(String keyword) {
        return List.of();
    }

    @Override
    public List<MutopiaAlbum> findAlbumByKeyword(String keyword, int limit, int offset) {
        List<AlbumSimplified> spotifyAlbums = spotifyAlbumRepository.findAlbumByKeyword(keyword, limit, offset);
        List<MutopiaAlbum> albums = spotifyAlbums.stream().map(DomainConvertor::toMutopia).toList();

        mutopiaAlbumRepository.saveAll(albums);

        return albums;
    }


}
