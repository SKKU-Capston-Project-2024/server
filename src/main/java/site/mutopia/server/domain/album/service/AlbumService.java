package site.mutopia.server.domain.album.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import site.mutopia.server.domain.album.dto.response.AlbumDetailResDto;
import site.mutopia.server.domain.album.entity.AlbumEntity;
import site.mutopia.server.domain.album.exception.AlbumNotFoundException;
import site.mutopia.server.domain.album.repository.AlbumRepository;
import site.mutopia.server.domain.song.entity.SongEntity;
import site.mutopia.server.spotify.SpotifyApi;
import site.mutopia.server.spotify.convertor.DomainConvertor;
import site.mutopia.server.spotify.dto.item.Track;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlbumService {

    private final AlbumRepository albumRepository;
    private final SpotifyApi spotifyApi;

    public AlbumDetailResDto findAlbumById(String albumId) {

        AlbumEntity albumEntity = albumRepository.findAlbumById(albumId).orElseThrow(() -> new AlbumNotFoundException("Album not found. albumId: " + albumId + " does not exist."));

        //Refactor later: highly coupled with Spotify API
        List<SongEntity> tracks = spotifyApi.getAlbumTracks(albumId).getItems().stream().map(item -> {
            return DomainConvertor.toDomain(item, albumEntity.getId());
        }).toList();

        albumEntity.setSongs(tracks);


        return AlbumDetailResDto.toDto(albumEntity);
    }

    public void findAlbumByAlbumName(String albumName) {

    }

    public List<AlbumEntity> searchAlbumByKeyword(String keyword) {
        List<AlbumEntity> albumByKeyword = albumRepository.findAlbumByKeyword(keyword, 10, 0);
        return albumByKeyword;
    }

}
