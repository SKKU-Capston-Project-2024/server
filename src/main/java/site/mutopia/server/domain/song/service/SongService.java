package site.mutopia.server.domain.song.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.mutopia.server.domain.album.entity.AlbumEntity;
import site.mutopia.server.domain.album.repository.AlbumEntityRepository;
import site.mutopia.server.domain.song.SongNotFoundException;
import site.mutopia.server.domain.song.dto.SongInfoDto;
import site.mutopia.server.domain.song.dto.SongSearchResDto;
import site.mutopia.server.domain.song.entity.SongEntity;
import site.mutopia.server.domain.song.repository.SongRepository;
import site.mutopia.server.domain.user.entity.UserEntity;
import site.mutopia.server.spotify.SpotifyApi;
import site.mutopia.server.spotify.convertor.DomainConvertor;
import site.mutopia.server.spotify.dto.track.Tracks;
import site.mutopia.server.spotify.dto.trackinfo.TrackInfo;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SongService {

    private final SpotifyApi spotifyApi;
    private final AlbumEntityRepository albumRepository;
    private final SongRepository songRepository;

    public List<SongSearchResDto> search(String query, int offset) {
        Tracks tracks = spotifyApi.searchTracks(query, 10, offset);
        List<SongSearchResDto> songs = tracks.items.stream().map(item -> {
            AlbumEntity albumEntity = albumRepository.findById(item.album.id).orElseGet(() ->
                    albumRepository.save(DomainConvertor.toDomain(item.album)));

            SongEntity song = DomainConvertor.toDomain(item);
            song.setAlbum(albumEntity);
            SongEntity entity = songRepository.save(song);

            return new SongSearchResDto(entity, item.album.images.get(0).url, item.artists.get(0).name);
        }).toList();

        return songs;

    }

    public SongInfoDto getSong(UserEntity user,String songId) {
        SongInfoDto songInfoDto = songRepository.findInfoById(user == null ? null : user.getId(), songId)
                .orElse(null);

        if(songInfoDto == null) {
            TrackInfo trackInfo = spotifyApi.getTrackInfo(songId);
            if(trackInfo == null) {
                throw new SongNotFoundException(songId);
            }
            AlbumEntity albumEntity = albumRepository.findById(trackInfo.album.id).orElseGet(() ->
                    albumRepository.save(DomainConvertor.toDomain(trackInfo.album)));

            SongEntity song = DomainConvertor.toDomain(trackInfo, albumEntity.getId());
            song.setAlbum(albumEntity);
            SongEntity entity = songRepository.save(song);

            return SongInfoDto.fromEntity(entity);
        }

        return songInfoDto;
    }




}
