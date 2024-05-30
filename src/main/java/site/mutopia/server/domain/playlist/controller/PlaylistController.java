package site.mutopia.server.domain.playlist.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.mutopia.server.domain.auth.annotation.LoginUser;
import site.mutopia.server.domain.playlist.dto.*;
import site.mutopia.server.domain.playlist.entity.PlaylistEntity;
import site.mutopia.server.domain.playlist.service.PlaylistService;
import site.mutopia.server.domain.user.entity.UserEntity;
import site.mutopia.server.spotify.dto.playlist.SpotifyPlaylistDetails;
import site.mutopia.server.spotify.dto.recommendation.RecommendationsDto;
import site.mutopia.server.spotify.entity.SpotifyTokenEntity;
import site.mutopia.server.spotify.entity.SpotifyTokenType;
import site.mutopia.server.spotify.exception.SpotifyAccessTokenNotFoundException;
import site.mutopia.server.spotify.repository.SpotifyTokenRepository;
import site.mutopia.server.spotify.service.SpotifyService;
import site.mutopia.server.swagger.response.CreatedResponse;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Tag(name = "Playlist", description = "Playlist APIs")
public class PlaylistController {

    private final PlaylistService playlistService;
    private final SpotifyService spotifyService;
    private final SpotifyTokenRepository spotifyTokenRepository;

    @Operation(summary = "플레이리스트 저장하기", description = "로그인 한 사용자는 플레이리스트를 저장할 수 있습니다.")
    @PostMapping("/user/playlist")
    @CreatedResponse
    public ResponseEntity<PlaylistSaveResDto> savePlaylist(@LoginUser UserEntity loggedInUser, @RequestBody PlaylistSaveReqDto dto) {
        PlaylistEntity savedPlaylist = playlistService.savePlaylist(loggedInUser.getId(), dto);

        PlaylistSaveResDto result = PlaylistSaveResDto.builder()
                .playlistId(savedPlaylist.getId()).build();

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @Operation(summary = "플레이리스트에 곡 추가하기", description = "로그인 한 사용자는 플레이리스트에 곡을 추가할 수 있습니다.")
    @PostMapping("/user/playlist/{playlistId}/song")
    @CreatedResponse
    public ResponseEntity<Void> addSongToPlaylist(@LoginUser UserEntity loggedInUser, @PathVariable("playlistId") Long playlistId, @RequestBody AddSongToPlaylistReqDto dto) {
        // TODO: loggedInUser가 해당 playlist를 소유하고 있는지 체크하는 로직 추가

        playlistService.addSongToPlaylist(playlistId, dto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "플레이리스트에서 곡 삭제하기", description = "로그인 한 사용자는 플레이리스트에 곡을 추가할 수 있습니다.")
    @DeleteMapping("/user/playlist/{playlistId}/song/{songId}")
    public ResponseEntity<Void> deleteSongFromPlaylist(@LoginUser UserEntity loggedInUser, @PathVariable("playlistId") Long playlistId, @PathVariable("songId") String songId) {
        // TODO: loggedInUser가 해당 playlist를 소유하고 있는지 체크하는 로직 추가

        playlistService.deleteSongFromPlaylist(playlistId, songId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "플레이리스트 삭제하기", description = "로그인 한 사용자는 플레이리스트를 삭제할 수 있습니다.")
    @DeleteMapping("/user/playlist/{playlistId}")
    public ResponseEntity<Void> deletePlaylist(@LoginUser UserEntity loggedInUser, @PathVariable("playlistId") Long playlistId) {
        // TODO: loggedInUser가 해당 playlist를 소유하고 있는지 체크하는 로직 추가

        playlistService.deletePlaylist(playlistId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "유저의 플레이리스트 목록 조회하기", description = "사용자는 플레이리스트 목록을 조회할 수 있습니다.")
    @GetMapping("/user/{userId}/playlist")
    public ResponseEntity<List<PlaylistInfoDto>> getUserPlaylists(@LoginUser UserEntity loggedInUser, @PathVariable("userId") String userId,
                                                   @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit) {
        List<PlaylistInfoDto> userPlaylists = playlistService.getUserPlaylists(userId, limit);
        return ResponseEntity.ok().body(userPlaylists);
    }

    @Operation(summary = "최근에 등록된 플레이리스트 조회하기", description = "사용자는 최근에 등록된 플레이리스트 목록을 조회할 수 있습니다.")
    @GetMapping("/user/playlist/recent")
    public ResponseEntity<List<PlaylistInfoDto>> getRecentPlaylists(@LoginUser UserEntity loggedInUser, @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit) {
        List<PlaylistInfoDto> userPlaylists = playlistService.getRecentPlaylists(limit, loggedInUser.getId());
        return ResponseEntity.ok().body(userPlaylists);
    }

    @Operation(summary = "플레이리스트 ID로 플레이리스트 단건 조회하기", description = "사용자는 플레이리스트 정보를 조회할 수 있습니다.")
    @GetMapping("/user/playlist/{playlistId}")
    public ResponseEntity<PlaylistInfoDto> getUserPlaylistById(@LoginUser UserEntity loggedInUser, @PathVariable("playlistId") Long playlistId) {
        PlaylistInfoDto playlistInfo = playlistService.getUserPlaylistById(playlistId, loggedInUser.getId());
        return ResponseEntity.ok().body(playlistInfo);
    }

    @Operation(summary = "플레이리스트 Spotify로 export 하기", description = "로그인 한 사용자는 자신의 플레이리스트를 Spotify에 export 할 수 있습니다. (spotify login 필요함)")
    @PostMapping("/user/playlist/{playlistId}/export/spotify")
    @CreatedResponse
    public ResponseEntity<SpotifyPlaylistDetails> exportPlaylistToSpotify(@LoginUser UserEntity loggedInUser, @PathVariable("playlistId") Long playlistId, @RequestBody ExportPlaylistToSpotifyReqDto req) {
        // TODO: loggedInUser가 해당 playlist를 소유하고 있는지 체크하는 로직 추가

        SpotifyTokenEntity spotifyAccessToken = spotifyTokenRepository.findByUserIdAndTokenType(loggedInUser.getId(), SpotifyTokenType.ACCESS)
                .orElseThrow(() -> new SpotifyAccessTokenNotFoundException("userId: " + loggedInUser.getId() + "has not logged In spotify before. please log in to spotify first."));

        List<String> songIdsInPlaylist = playlistService.getUserPlaylistById(playlistId, loggedInUser.getId()).getSongs().stream().map(song -> song.getSongId()).toList();

        String spotifyPlaylistId = spotifyService.createPlaylist(spotifyAccessToken, req.getPlaylist().getName(), req.getPlaylist().getDescription());
        spotifyService.addSongsToPlaylist(spotifyAccessToken, spotifyPlaylistId, songIdsInPlaylist);

        SpotifyPlaylistDetails playlistDetails = spotifyService.getPlaylistDetails(spotifyAccessToken, spotifyPlaylistId);

        return ResponseEntity.status(HttpStatus.CREATED).body(playlistDetails);
    }

    @Operation(summary = "플레이리스트로 추천 곡 목록 조회하기", description = "사용자는 플레이리스트를 통해 추천 곡 목록을 조회할 수 있다. (spotify login 필요함)")
    @GetMapping("/user/playlist/{playlistId}/recommendation")
    public ResponseEntity<RecommendationsDto> getRecommendationsByPlaylistId(@LoginUser UserEntity loggedInUser, @PathVariable("playlistId") Long playlistId) {
        SpotifyTokenEntity spotifyAccessToken = spotifyTokenRepository.findByUserIdAndTokenType(loggedInUser.getId(), SpotifyTokenType.ACCESS)
                .orElseThrow(() -> new SpotifyAccessTokenNotFoundException("userId: " + loggedInUser.getId() + "has not logged In spotify before. please log in to spotify first."));

        List<String> songIdsInPlaylist = playlistService.getUserPlaylistById(playlistId, loggedInUser.getId()).getSongs().stream().map(song -> song.getSongId()).limit(5).toList();
        RecommendationsDto recommendations = spotifyService.getRecommendations(songIdsInPlaylist, spotifyAccessToken.getTokenValue());

        return ResponseEntity.ok().body(recommendations);
    }
}
