package site.mutopia.server.domain.albumLike.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.mutopia.server.domain.albumLike.dto.AlbumLikeStatusResDto;
import site.mutopia.server.domain.albumLike.dto.AlbumLikeToggleResDto;
import site.mutopia.server.domain.albumLike.dto.AlbumLikeToggleResDto.AlbumLikeToggleStatus;
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
    @Operation(summary = "좋아요 버튼 토글", description = "로그인 한 유저가 좋아요 버튼을 토글하는 API")
    @OkResponse
    public ResponseEntity<AlbumLikeToggleResDto> toggleLike(@PathVariable("albumId") String albumId, @LoginUser UserEntity loggedInUser) {
        albumLikeService.toggleAlbumLike(albumId, loggedInUser.getId());
        boolean albumLikeExists = albumLikeService.isAlbumLikeExists(albumId, loggedInUser.getId());

        AlbumLikeToggleResDto resDto = AlbumLikeToggleResDto
                .builder()
                .likeStatus(albumLikeExists ? AlbumLikeToggleStatus.ON : AlbumLikeToggleStatus.OFF)
                .build();

        return ResponseEntity.ok().body(resDto);
    }

    @GetMapping("/{albumId}/like/status")
    @Operation(summary = "좋아요 상태 조회", description = "로그인 한 유저가 앨범에 좋아요를 눌렀는지를 조회하는 API")
    @OkResponse
    public ResponseEntity<AlbumLikeStatusResDto> getAlbumLikeStatus(@PathVariable("albumId") String albumId, @LoginUser UserEntity loggedInUser) {
        boolean likeStatus = albumLikeService.checkAlbumLikeStatus(albumId, loggedInUser);
        return ResponseEntity.ok(new AlbumLikeStatusResDto(likeStatus));
    }
}
