package site.mutopia.server.domain.playlistLike.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.mutopia.server.domain.auth.annotation.LoginUser;
import site.mutopia.server.domain.playlist.dto.PlaylistInfoDto;
import site.mutopia.server.domain.playlist.service.PlaylistService;
import site.mutopia.server.domain.playlistLike.dto.PlaylistLikeResDto;
import site.mutopia.server.domain.playlistLike.dto.PlaylistLikeResDto.PlaylistLikeToggleResStatus;
import site.mutopia.server.domain.playlistLike.dto.PlaylistLikeStatusResDto;
import site.mutopia.server.domain.playlistLike.dto.PlaylistLikeStatusResDto.IsUserLoggedIn;
import site.mutopia.server.domain.playlistLike.dto.PlaylistLikeStatusResDto.PlaylistLikeToggleStatus;
import site.mutopia.server.domain.playlistLike.entity.PlaylistLikeEntity;
import site.mutopia.server.domain.playlistLike.service.PlaylistLikeService;
import site.mutopia.server.domain.user.entity.UserEntity;
import site.mutopia.server.swagger.response.OkResponse;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Tag(name = "Playlist Like", description = "Playlist Like APIs")
public class PlaylistLikeController {

    private final PlaylistLikeService playlistLikeService;
    private final PlaylistService playlistService;

    @PostMapping("/user/playlist/{playlistId}/like/toggle")
    @Operation(summary = "플레이리스트 좋아요 버튼 토글하기", description = "로그인 한 사용자는 플레이리스트 좋아요를 토글할 수 있습니다.")
    public ResponseEntity<PlaylistLikeResDto> togglePlaylistLike(@LoginUser UserEntity loggedInUser, @PathVariable("playlistId") Long playlistId) {
        playlistLikeService.toggleLike(loggedInUser, playlistId);

        boolean playlistLikeExists = playlistLikeService.isPlaylistLikeExists(playlistId, loggedInUser.getId());
        Long playlistLikeCount = playlistLikeService.countLikesByPlaylistId(playlistId);

        PlaylistLikeResDto dto = PlaylistLikeResDto.builder()
                .likeStatus(playlistLikeExists ? PlaylistLikeToggleResStatus.ON : PlaylistLikeToggleResStatus.OFF)
                .likeCount(playlistLikeCount)
                .build();

        return ResponseEntity.ok().body(dto);
    }


    @Operation(summary = "플레이리스트에 대한 좋아요 상태 조회", description = "사용자는 어떤 플레이리스트에 대한 좋아요 상태를 조회할 수 있습니다. 로그인 하지 않은 경우, isUserLoggedIn=NO, likeStatus=NULL")
    @GetMapping("/user/playlist/{playlistId}/like/status")
    @OkResponse
    public ResponseEntity<PlaylistLikeStatusResDto> getPlaylistLikeStatus(@LoginUser(require = false) UserEntity loggedInUser, @PathVariable("playlistId") Long playlistId) {
        boolean isUserLoggedIn = loggedInUser != null;

        Long likeCount = playlistLikeService.countLikesByPlaylistId(playlistId);
        boolean likeExists = isUserLoggedIn && playlistLikeService.isPlaylistLikeExists(playlistId, loggedInUser.getId());

        PlaylistLikeToggleStatus likeStatus = isUserLoggedIn ? (likeExists ? PlaylistLikeToggleStatus.ON : PlaylistLikeToggleStatus.OFF) : PlaylistLikeToggleStatus.NULL;
        PlaylistLikeStatusResDto dto = PlaylistLikeStatusResDto.builder()
                .isUserLoggedIn(isUserLoggedIn ? IsUserLoggedIn.YES : IsUserLoggedIn.NO)
                .likeStatus(likeStatus)
                .likeCount(likeCount)
                .build();
        return ResponseEntity.ok().body(dto);
    }

    @Operation(summary = "특정 유저가 좋아요한 플레이리스트 조회", description = "특정 유저가 좋아요한 플레이리스트를 조회합니다.")
    @GetMapping("/user/{userId}/playlist/like")
    public ResponseEntity<List<PlaylistInfoDto>> getLikedPlaylistsByUser(@PathVariable("userId") String userId, @RequestParam("page") Integer page){
        return ResponseEntity.ok().body(playlistService.getLikedPlaylistsByUserId(userId, page));
    }

}
