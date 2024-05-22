package site.mutopia.server.domain.songLike.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.mutopia.server.domain.songLike.dto.SongLikeStatusResDto;
import site.mutopia.server.domain.songLike.dto.SongLikeStatusResDto.SongLikeToggleStatus;
import site.mutopia.server.domain.songLike.dto.SongLikeStatusResDto.IsUserLoggedIn;
import site.mutopia.server.domain.songLike.dto.SongLikeToggleResDto;
import site.mutopia.server.domain.songLike.dto.SongLikeToggleResDto.SongLikeToggleResStatus;
import site.mutopia.server.domain.songLike.service.SongLikeService;
import site.mutopia.server.domain.auth.annotation.LoginUser;
import site.mutopia.server.domain.user.entity.UserEntity;
import site.mutopia.server.swagger.response.OkResponse;

@RestController
@RequestMapping("/song")
@RequiredArgsConstructor
@Tag(name = "Song Like", description = "Song Like APIs")
public class SongLikeController {

    private final SongLikeService songLikeService;

    @PostMapping("/{songId}/like/toggle")
    @Operation(summary = "곡의 좋아요 버튼 토글하기", description = "로그인 한 사용자는 곡의 좋아요 버튼을 토글할 수 있다.")
    @OkResponse
    public ResponseEntity<SongLikeToggleResDto> toggleLikeToSong(@PathVariable("songId") String songId, @LoginUser UserEntity loggedInUser) {
        songLikeService.toggleSongLike(songId, loggedInUser.getId());
        boolean likeExists = songLikeService.isSongLikeExists(songId, loggedInUser.getId());
        Long likeCount = songLikeService.countLikesBySongId(songId);

        SongLikeToggleResDto resDto = SongLikeToggleResDto
                .builder()
                .likeStatus(likeExists ? SongLikeToggleResStatus.ON : SongLikeToggleResStatus.OFF)
                .likeCount(likeCount)
                .build();

        return ResponseEntity.ok().body(resDto);
    }

    @GetMapping("/{songId}/like/status")
    @Operation(summary = "곡에 대한 좋아요 상태 조회", description = "사용자는 어떤 곡에 대한 좋아요 상태를 조회할 수 있습니다. 로그인 하지 않은 경우, isUserLoggedIn=NO, likeStatus=NULL")
    @OkResponse
    public ResponseEntity<SongLikeStatusResDto> getSongLikeStatus(@PathVariable("songId") String songId, @LoginUser(require = false) UserEntity loggedInUser) {
        boolean isUserLoggedIn = loggedInUser != null;

        Long likeCount = songLikeService.countLikesBySongId(songId);
        boolean likeExists = isUserLoggedIn && songLikeService.isSongLikeExists(songId, loggedInUser.getId());

        SongLikeToggleStatus likeStatus = isUserLoggedIn ? (likeExists ? SongLikeToggleStatus.ON : SongLikeToggleStatus.OFF) : SongLikeToggleStatus.NULL;
        SongLikeStatusResDto dto = SongLikeStatusResDto.builder()
                .isUserLoggedIn(isUserLoggedIn ? IsUserLoggedIn.YES : IsUserLoggedIn.NO)
                .likeStatus(likeStatus)
                .likeCount(likeCount)
                .build();

        return ResponseEntity.ok().body(dto);
    }
}
