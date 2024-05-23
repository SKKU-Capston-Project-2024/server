package site.mutopia.server.domain.playlist.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import site.mutopia.server.domain.playlist.dto.AddSongToPlaylistReqDto;
import site.mutopia.server.domain.playlist.dto.PlaylistInfoDto;
import site.mutopia.server.domain.playlist.dto.PlaylistSaveReqDto;
import site.mutopia.server.domain.playlist.entity.PlaylistEntity;
import site.mutopia.server.domain.playlist.exception.PlaylistNotFoundException;
import site.mutopia.server.domain.playlist.repository.PlaylistRepository;
import site.mutopia.server.domain.playlistSong.entity.PlaylistSongEntity;
import site.mutopia.server.domain.playlistSong.repository.PlaylistSongRepository;
import site.mutopia.server.domain.song.entity.SongEntity;
import site.mutopia.server.domain.song.exception.SongNotFoundException;
import site.mutopia.server.domain.song.repository.SongRepository;
import site.mutopia.server.domain.user.entity.UserEntity;
import site.mutopia.server.domain.user.exception.UserNotFoundException;
import site.mutopia.server.domain.user.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final PlaylistSongRepository playlistSongRepository;
    private final SongRepository songRepository;
    private final UserRepository userRepository;

    // TODO: 성능 개선
    public List<PlaylistInfoDto> getUserPlaylists(String userId, int limit) {
        List<PlaylistInfoDto> playlists = playlistRepository.findPlaylistInfosByUserId(userId, PageRequest.of(0, limit)).getContent();

        playlists.forEach(playlist -> playlist.setSongs(fetchSongsForPlaylist(playlist.getPlaylistId())));

        return playlists;
    }

    public PlaylistInfoDto getUserPlaylistById(Long playlistId) {
        PlaylistInfoDto playlistInfoDto = playlistRepository.findPlaylistInfoById(playlistId).orElseThrow(() -> new PlaylistNotFoundException("Playlist not found. playlistId: " + playlistId + " does not exist."));
        playlistInfoDto.setSongs(fetchSongsForPlaylist(playlistId));

        return playlistInfoDto;
    }

    public List<PlaylistInfoDto> getRecentPlaylists(int limit) {
        List<PlaylistInfoDto> playlists = playlistRepository.findRecentPlaylists(PageRequest.of(0, limit)).getContent();

        playlists.forEach(playlist -> playlist.setSongs(fetchSongsForPlaylist(playlist.getPlaylistId())));

        return playlists;
    }

    private List<PlaylistInfoDto.SongBriefInfo> fetchSongsForPlaylist(Long playlistId) {
        return playlistSongRepository.findByPlaylistId(playlistId).stream()
                .map(playlistSong -> new PlaylistInfoDto.SongBriefInfo(playlistSong.getSong().getId(),
                        playlistSong.getSong().getTitle(),
                        playlistSong.getListTrackOrder()))
                .collect(Collectors.toList());
    }

    public PlaylistEntity savePlaylist(String userId, PlaylistSaveReqDto dto) {
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found. userId: " + userId + " does not exist."));
        return playlistRepository.save(PlaylistEntity.builder().title(dto.getTitle()).content(dto.getContent()).creator(user).build());
    }

    public PlaylistSongEntity addSongToPlaylist(Long playlistId, AddSongToPlaylistReqDto dto) {
        PlaylistEntity playlist = playlistRepository.findById(playlistId).orElseThrow(() -> new PlaylistNotFoundException("Playlist not found. playlistId: " + playlistId + " does not exist."));
        SongEntity song = songRepository.findById(dto.getSongId()).orElseThrow(() -> new SongNotFoundException("Song not found. songId: " + dto.getSongId() + " does not exist."));

        PlaylistSongEntity playlistSong = PlaylistSongEntity
                .builder()
                .playlist(playlist)
                .song(song)
                .listTrackOrder(dto.getTrackOrder())
                .build();

        return playlistSongRepository.save(playlistSong);
    }

    public void deleteSongFromPlaylist(Long playlistId, String songId) {
        playlistSongRepository.deleteByPlaylistIdAndSongId(playlistId, songId);
    }

    public void deletePlaylist(Long playlistId) {
        playlistSongRepository.deleteByPlaylistId(playlistId);
        playlistRepository.deleteById(playlistId);
    }

    public List<PlaylistInfoDto> getLikedPlaylistsByUserId(String userId, int page) {
        List<PlaylistInfoDto> info = playlistRepository.findLikedPlayList(userId, Pageable.ofSize(20).withPage(page)).getContent();
        info.forEach(playlistInfoDto -> playlistInfoDto.setSongs(fetchSongsForPlaylist(playlistInfoDto.getPlaylistId())));
        return info;
    }
}