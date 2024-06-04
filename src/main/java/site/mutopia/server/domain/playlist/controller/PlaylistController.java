package site.mutopia.server.domain.playlist.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
import site.mutopia.server.spotify.exception.SpotifyAccessTokenExpiredException;
import site.mutopia.server.spotify.exception.SpotifyAccessTokenNotFoundException;
import site.mutopia.server.spotify.exception.SpotifyRefreshTokenNotFoundException;
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

    @Value("${spotify.user.id}")
    private String spotifyUserId;

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
    public ResponseEntity<List<PlaylistInfoDto>> getUserPlaylists(
            @LoginUser(require = false) UserEntity loggedInUser,
            @PathVariable("userId") String userId,
            @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit) {
        List<PlaylistInfoDto> userPlaylists = playlistService.getUserPlaylists(userId, limit, loggedInUser != null ? loggedInUser.getId() : null);
        return ResponseEntity.ok().body(userPlaylists);
    }

    @Operation(summary = "최근에 등록된 플레이리스트 조회하기", description = "사용자는 최근에 등록된 플레이리스트 목록을 조회할 수 있습니다.")
    @GetMapping("/user/playlist/recent")
    public ResponseEntity<List<PlaylistInfoDto>> getRecentPlaylists(@LoginUser(require = false) UserEntity loggedInUser, @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit) {
        List<PlaylistInfoDto> userPlaylists = playlistService.getRecentPlaylists(limit, loggedInUser != null ? loggedInUser.getId() : null);
        return ResponseEntity.ok().body(userPlaylists);
    }

    @Operation(summary = "플레이리스트 ID로 플레이리스트 단건 조회하기", description = "사용자는 플레이리스트 정보를 조회할 수 있습니다.")
    @GetMapping("/user/playlist/{playlistId}")
    public ResponseEntity<PlaylistInfoDto> getUserPlaylistById(@LoginUser(require = false) UserEntity loggedInUser, @PathVariable("playlistId") Long playlistId) {
        PlaylistInfoDto playlistInfo = playlistService.getUserPlaylistByIdAndUserId(playlistId, loggedInUser != null ? loggedInUser.getId() : null);
        return ResponseEntity.ok().body(playlistInfo);
    }

    @Operation(summary = "플레이리스트 Spotify로 export 하기", description = "로그인 한 사용자는 자신 또는 다른 사용자의 플레이리스트를 Spotify에 export 할 수 있습니다. (spotify login 필요함)")
    @PostMapping("/user/playlist/{playlistId}/export/spotify")
    @CreatedResponse
    public ResponseEntity<ExportPlaylistToSpotifyResDto> exportPlaylistToSpotify(@LoginUser UserEntity loggedInUser, @PathVariable("playlistId") Long playlistId, @RequestBody ExportPlaylistToSpotifyReqDto req) {

        SpotifyTokenEntity spotifyAccessToken = spotifyTokenRepository.findByUserIdAndTokenType(spotifyUserId, SpotifyTokenType.ACCESS)
                .orElseThrow(() -> new SpotifyAccessTokenNotFoundException("(spotify user) userId: " + spotifyUserId + "has not logged In spotify before. please log in to spotify first."));

        List<String> songIdsInPlaylist = playlistService.getUserPlaylistById(playlistId).getSongs().stream().map(song -> song.getSongId()).toList();

        SpotifyPlaylistDetails playlistDetails;
        try {
            String spotifyPlaylistId = spotifyService.createPlaylist(spotifyAccessToken, req.getPlaylist().getName(), req.getPlaylist().getDescription());
            spotifyService.addSongsToPlaylist(spotifyAccessToken, spotifyPlaylistId, songIdsInPlaylist);
            playlistDetails = spotifyService.getPlaylistDetails(spotifyAccessToken, spotifyPlaylistId);
        } catch (SpotifyAccessTokenExpiredException ex) {
            SpotifyTokenEntity updatedAccessToken = refreshToken();

            String spotifyPlaylistId = spotifyService.createPlaylist(updatedAccessToken, req.getPlaylist().getName(), req.getPlaylist().getDescription());
            spotifyService.addSongsToPlaylist(updatedAccessToken, spotifyPlaylistId, songIdsInPlaylist);
            playlistDetails = spotifyService.getPlaylistDetails(updatedAccessToken, spotifyPlaylistId);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(ExportPlaylistToSpotifyResDto.builder().url("https://open.spotify.com/playlist/" + playlistDetails.getId()).build());
    }

    @Operation(summary = "플레이리스트로 추천 곡 목록 조회하기", description = "사용자는 플레이리스트를 통해 추천 곡 목록을 조회할 수 있다. (spotify login 필요함)")
    @GetMapping("/user/playlist/{playlistId}/recommendation")
    public ResponseEntity<PlaylistRecommendationResDto> getRecommendationsByPlaylistId(@LoginUser UserEntity loggedInUser, @PathVariable("playlistId") Long playlistId) {
        SpotifyTokenEntity spotifyAccessToken = spotifyTokenRepository.findByUserIdAndTokenType(spotifyUserId, SpotifyTokenType.ACCESS)
                .orElseThrow(() -> new SpotifyAccessTokenNotFoundException("(spotify user) userId: " + spotifyUserId + "has not logged In spotify before. please log in to spotify first."));

        List<String> songIdsInPlaylist = playlistService.getUserPlaylistByIdAndUserId(playlistId, loggedInUser.getId()).getSongs().stream().map(song -> song.getSongId()).limit(5).toList();

        RecommendationsDto recommendations;

        try {
            recommendations = spotifyService.getRecommendations(songIdsInPlaylist, spotifyAccessToken.getTokenValue());
        } catch (SpotifyAccessTokenExpiredException ex) {
            SpotifyTokenEntity updatedAccessToken = refreshToken();
            recommendations = spotifyService.getRecommendations(songIdsInPlaylist, updatedAccessToken.getTokenValue());
        }

        return ResponseEntity.ok().body(recommendations.toDto());
    }

    @Operation(summary = "Trending API", description = "Global top 50 플레이리스트 가져오기")
    @GetMapping("/playlist/trending")
    public ResponseEntity<PlaylistTrendingResDto> getGlobalTop50() {
        String globalTop50PlaylistId = "37i9dQZEVXbMDoHDwVN2tF";

        SpotifyTokenEntity spotifyAccessToken = spotifyTokenRepository.findByUserIdAndTokenType(spotifyUserId, SpotifyTokenType.ACCESS)
                .orElseThrow(() -> new SpotifyAccessTokenNotFoundException("(spotify user) userId: " + spotifyUserId + "has not logged In spotify before. please log in to spotify first."));

        SpotifyPlaylistDetails playlistDetails;
        try {
            playlistDetails = spotifyService.getPlaylistDetails(spotifyAccessToken, globalTop50PlaylistId);
        } catch (SpotifyAccessTokenExpiredException ex) {
            SpotifyTokenEntity updatedAccessToken = refreshToken();
            playlistDetails = spotifyService.getPlaylistDetails(updatedAccessToken, globalTop50PlaylistId);
        }

        return ResponseEntity.ok().body(playlistDetails.toDto());
    }

    @Operation(summary = "특정 앨범이 포함된 플레이리스트가져오기 (최신순)")
    @GetMapping("/user/playlist/album/{albumId}/recent")
    public ResponseEntity<List<PlaylistInfoDto>> getPlaylistsByAlbumId(
            @LoginUser(require = false) UserEntity loggedInUser,
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @PathVariable("albumId") String albumId) {
        List<PlaylistInfoDto> playlists = playlistService.getPlaylistsByAlbumId(albumId, page, loggedInUser != null ? loggedInUser.getId() : null);
        return ResponseEntity.ok().body(playlists);
    }

    @Operation(summary = "특정 트랙이 포함된 플레이리스트가져오기 (최신순)")
    @GetMapping("/user/playlist/song/{songId}/recent")
    public ResponseEntity<List<PlaylistInfoDto>> getPlaylistsBySongId(
            @LoginUser(require = false) UserEntity loggedInUser,
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @PathVariable("songId") String songId) {
        List<PlaylistInfoDto> playlists = playlistService.getPlaylistsBySongId(songId, page, loggedInUser != null ? loggedInUser.getId() : null);
        return ResponseEntity.ok().body(playlists);
    }

    @Operation(summary = "특정 앨범이 포함된 플레이리스트가져오기 (인기순)")
    @GetMapping("/user/playlist/album/{albumId}/popular")
    public ResponseEntity<List<PlaylistInfoDto>> getPopularPlaylistsByAlbumId(
            @LoginUser(require = false) UserEntity loggedInUser,
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @PathVariable("albumId") String albumId) {
        List<PlaylistInfoDto> playlists = playlistService.getPopularPlaylistsByAlbumId(albumId, page, loggedInUser != null ? loggedInUser.getId() : null);
        return ResponseEntity.ok().body(playlists);
    }

    @Operation(summary = "특정 트랙이 포함된 플레이리스트가져오기 (인기순)")
    @GetMapping("/user/playlist/song/{songId}/popular")
    public ResponseEntity<List<PlaylistInfoDto>> getPopularPlaylistsBySongId(
            @LoginUser(require = false) UserEntity loggedInUser,
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @PathVariable("songId") String songId) {
        List<PlaylistInfoDto> playlists = playlistService.getPopularPlaylistsBySongId(songId, page, loggedInUser != null ? loggedInUser.getId() : null);
        return ResponseEntity.ok().body(playlists);
    }


    private SpotifyTokenEntity refreshToken() {
        SpotifyTokenEntity refreshToken = spotifyTokenRepository.findByUserIdAndTokenType(spotifyUserId, SpotifyTokenType.REFRESH)
                .orElseThrow(() -> new SpotifyRefreshTokenNotFoundException("(spotify user) userId: " + spotifyUserId + "doesn't have refresh token"));
        String accessToken = spotifyService.refreshAccessToken(refreshToken.getTokenValue());
        spotifyService.updateAccessToken(spotifyUserId, accessToken);
        SpotifyTokenEntity updatedAccessToken = spotifyTokenRepository.findByUserIdAndTokenType(spotifyUserId, SpotifyTokenType.ACCESS)
                .orElseThrow(() -> new SpotifyRefreshTokenNotFoundException("(spotify user) userId: " + spotifyUserId + "doesn't have access token"));

        return updatedAccessToken;
    }
}
