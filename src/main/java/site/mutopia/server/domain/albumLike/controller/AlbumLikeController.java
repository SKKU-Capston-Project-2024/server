package site.mutopia.server.domain.albumLike.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.mutopia.server.domain.albumLike.dto.AlbumLikeStatusResDto;
import site.mutopia.server.domain.albumLike.dto.AlbumLikeStatusResDto.AlbumLikeToggleStatus;
import site.mutopia.server.domain.albumLike.dto.AlbumLikeStatusResDto.IsUserLoggedIn;
import site.mutopia.server.domain.albumLike.dto.AlbumLikeToggleResDto;
import site.mutopia.server.domain.albumLike.dto.AlbumLikeToggleResDto.AlbumLikeToggleResStatus;
import site.mutopia.server.domain.albumLike.service.AlbumLikeService;
import site.mutopia.server.domain.auth.annotation.LoginUser;
import site.mutopia.server.domain.user.entity.UserEntity;
import site.mutopia.server.swagger.response.OkResponse;

@RestController
@RequestMapping("/album")
@RequiredArgsConstructor
@Tag(name = "Album Like", description = "Album Like APIs")
public class AlbumLikeController {

    private final AlbumLikeService albumLikeService;

    @PostMapping("/{albumId}/like/toggle")
    @Operation(summary = "앨범의 좋아요 버튼 토글하기", description = "로그인 한 사용자는 앨범의 좋아요 버튼을 토글할 수 있다.")
    @OkResponse
    public ResponseEntity<AlbumLikeToggleResDto> toggleLikeToAlbum(@PathVariable("albumId") String albumId, @LoginUser UserEntity loggedInUser) {
        albumLikeService.toggleAlbumLike(albumId, loggedInUser.getId());
        boolean likeExists = albumLikeService.isAlbumLikeExists(albumId, loggedInUser.getId());
        Long likeCount = albumLikeService.countLikesByAlbumId(albumId);

        AlbumLikeToggleResDto resDto = AlbumLikeToggleResDto
                .builder()
                .likeStatus(likeExists ? AlbumLikeToggleResStatus.ON : AlbumLikeToggleResStatus.OFF)
                .likeCount(likeCount)
                .build();

        return ResponseEntity.ok().body(resDto);
    }

    @GetMapping("/{albumId}/like/status")
    @Operation(summary = "앨범에 대한 좋아요 상태 조회", description = "사용자는 어떤 앨범에 대한 좋아요 상태를 조회할 수 있습니다. 로그인 하지 않은 경우, isUserLoggedIn=NO, likeStatus=NULL")
    @OkResponse
    public ResponseEntity<AlbumLikeStatusResDto> getAlbumLikeStatus(@PathVariable("albumId") String albumId, @LoginUser(require = false) UserEntity loggedInUser) {
        boolean isUserLoggedIn = loggedInUser != null;

        Long likeCount = albumLikeService.countLikesByAlbumId(albumId);
        boolean likeExists = isUserLoggedIn && albumLikeService.isAlbumLikeExists(albumId, loggedInUser.getId());

        AlbumLikeToggleStatus likeStatus = isUserLoggedIn ? (likeExists ? AlbumLikeToggleStatus.ON : AlbumLikeToggleStatus.OFF) : AlbumLikeToggleStatus.NULL;
        AlbumLikeStatusResDto dto = AlbumLikeStatusResDto.builder()
                .isUserLoggedIn(isUserLoggedIn ? IsUserLoggedIn.YES : IsUserLoggedIn.NO)
                .likeStatus(likeStatus)
                .likeCount(likeCount)
                .build();

        return ResponseEntity.ok().body(dto);
    }
}
