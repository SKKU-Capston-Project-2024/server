package site.mutopia.server.domain.album.repository;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import site.mutopia.server.domain.album.entity.AlbumEntity;
import site.mutopia.server.domain.song.entity.SongEntity;
import site.mutopia.server.spotify.SpotifyApi;
import site.mutopia.server.spotify.convertor.DomainConvertor;
import site.mutopia.server.spotify.dto.item.Albums;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Transactional
public class AlbumRepositoryImpl implements AlbumRepository{

    private final SpotifyApi spotifyApi;
    private final AlbumEntityRepository AlbumEntityRepository;

    @Override
    public Optional<AlbumEntity> findAlbumById(String albumId) {
        Optional<AlbumEntity> album = AlbumEntityRepository.findById(albumId);
        if(album.isEmpty()){
            return Optional.empty();
        }
        List<SongEntity> songs = album.get().getSongs();
        if(songs == null||songs.isEmpty()){
            List<SongEntity> tracks = spotifyApi.getAlbumTracks(albumId).getItems().stream().map(item -> {
                return DomainConvertor.toDomain(item, album.get().getId());
            }).toList();

            album.get().setSongs(tracks);
            AlbumEntityRepository.save(album.get());
        }
        return album;
    }

    @Override
    public List<AlbumEntity> findAlbumByAlbumName(String albumName) {
        //TODO: implement
        return List.of();
    }

    @Override
    public List<AlbumEntity> findAlbumByArtistName(String artistName, int limit, int offset) {
        //TODO: implement
        return List.of();
    }

    @Override
    public List<AlbumEntity> findAlbumsByArtistNameOrAlbumName(String keyword) {
        return List.of();
    }

    @Override
    public List<AlbumEntity> findAlbumByKeyword(String keyword, int limit, int offset) {
        Albums spotifyAlbums = spotifyApi.searchAlbums(keyword, limit, offset);
        List<AlbumEntity> albums = spotifyAlbums.items.stream().map(DomainConvertor::toDomain).toList();

        AlbumEntityRepository.saveAll(albums);

        return albums;
    }


}
