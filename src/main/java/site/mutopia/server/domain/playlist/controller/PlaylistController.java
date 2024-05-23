package site.mutopia.server.domain.playlist.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.mutopia.server.domain.auth.annotation.LoginUser;
import site.mutopia.server.domain.playlist.dto.AddSongToPlaylistReqDto;
import site.mutopia.server.domain.playlist.dto.PlaylistInfoDto;
import site.mutopia.server.domain.playlist.dto.PlaylistSaveReqDto;
import site.mutopia.server.domain.playlist.dto.PlaylistSaveResDto;
import site.mutopia.server.domain.playlist.entity.PlaylistEntity;
import site.mutopia.server.domain.playlist.service.PlaylistService;
import site.mutopia.server.domain.user.entity.UserEntity;
import site.mutopia.server.swagger.response.CreatedResponse;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Tag(name = "Playlist", description = "Playlist APIs")
public class PlaylistController {

    private final PlaylistService playlistService;

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
    public ResponseEntity<Void> deletePlaylist(@PathVariable("playlistId") Long playlistId) {
        // TODO: loggedInUser가 해당 playlist를 소유하고 있는지 체크하는 로직 추가

        playlistService.deletePlaylist(playlistId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "유저의 플레이리스트 목록 조회하기", description = "사용자는 플레이리스트 목록을 조회할 수 있습니다.")
    @GetMapping("/user/{userId}/playlist")
    public ResponseEntity<List<PlaylistInfoDto>> getUserPlaylists(@PathVariable("userId") String userId,
                                                   @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit) {
        List<PlaylistInfoDto> userPlaylists = playlistService.getUserPlaylists(userId, limit);
        return ResponseEntity.ok().body(userPlaylists);
    }

    @Operation(summary = "최근에 등록된 플레이리스트 조회하기", description = "사용자는 최근에 등록된 플레이리스트 목록을 조회할 수 있습니다.")
    @GetMapping("/user/playlist/recent")
    public ResponseEntity<List<PlaylistInfoDto>> getRecentPlaylists(@RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit) {
        List<PlaylistInfoDto> userPlaylists = playlistService.getRecentPlaylists(limit);
        return ResponseEntity.ok().body(userPlaylists);
    }

    @Operation(summary = "플레이리스트 ID로 플레이리스트 단건 조회하기", description = "사용자는 플레이리스트 정보를 조회할 수 있습니다.")
    @GetMapping("/user/playlist/{playlistId}")
    public ResponseEntity<PlaylistInfoDto> getUserPlaylistById(@PathVariable("playlistId") Long playlistId) {
        PlaylistInfoDto playlistInfo = playlistService.getUserPlaylistById(playlistId);
        return ResponseEntity.ok().body(playlistInfo);
    }
}
