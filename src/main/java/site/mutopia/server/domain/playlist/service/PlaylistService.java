package site.mutopia.server.domain.playlist.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import site.mutopia.server.domain.playlist.dto.PlaylistInfoDto;
import site.mutopia.server.domain.playlist.repository.PlaylistRepository;
import site.mutopia.server.domain.playlistSong.repository.PlaylistSongRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final PlaylistSongRepository playlistSongRepository;

    // TODO: 성능 개선
    public List<PlaylistInfoDto> getUserPlaylists(String userId, int limit) {
        List<PlaylistInfoDto> playlists = playlistRepository.findPlaylistInfoByUserId(userId, PageRequest.of(0, limit)).getContent();

        playlists.forEach(playlist -> playlist.setSongs(fetchSongsForPlaylist(playlist.getPlaylistId())));

        return playlists;
    }

    private List<PlaylistInfoDto.SongBriefInfo> fetchSongsForPlaylist(Long playlistId) {
        return playlistSongRepository.findByPlaylistId(playlistId).stream()
                .map(playlistSong -> new PlaylistInfoDto.SongBriefInfo(playlistSong.getSong().getSongId(),
                        playlistSong.getSong().getTitle(),
                        playlistSong.getListTrackOrder()))
                .collect(Collectors.toList());
    }
}
