package site.mutopia.server.domain.albumReviewLike;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.mutopia.server.domain.albumReview.dto.AlbumReviewInfoDto;
import site.mutopia.server.domain.albumReviewLike.dto.AlbumReviewLikeResDto;
import site.mutopia.server.domain.albumReviewLike.dto.AlbumReviewLikeResDto.AlbumReviewLikeToggleResStatus;
import site.mutopia.server.domain.albumReviewLike.dto.AlbumReviewLikeStatusResDto;
import site.mutopia.server.domain.albumReviewLike.dto.AlbumReviewLikeStatusResDto.AlbumReviewLikeToggleStatus;
import site.mutopia.server.domain.albumReviewLike.dto.AlbumReviewLikeStatusResDto.IsUserLoggedIn;
import site.mutopia.server.domain.albumReviewLike.service.AlbumReviewLikeService;
import site.mutopia.server.domain.auth.annotation.LoginUser;
import site.mutopia.server.domain.user.entity.UserEntity;
import site.mutopia.server.swagger.response.OkResponse;

import java.util.List;

@RestController
@RequestMapping("/album/review")
@RequiredArgsConstructor
@Tag(name = "Album Review Like", description = "Album Review Like APIs")
public class AlbumReviewLikeController {

    private final AlbumReviewLikeService albumReviewLikeService;

    @Operation(summary = "앨범 리뷰의 좋아요 버튼 토글하기", description = "로그인 한 사용자는 앨범 리뷰의 좋아요 버튼을 토글할 수 있다.")
    @PostMapping("/{albumReviewId}/like/toggle")
    @OkResponse
    public ResponseEntity<AlbumReviewLikeResDto> toggleLikeToAlbumReview(@LoginUser UserEntity loggedInUser, @PathVariable("albumReviewId") Long albumReviewId) {
        albumReviewLikeService.toggleAlbumReviewLike(albumReviewId, loggedInUser.getId());
        boolean albumReviewLikeExists = albumReviewLikeService.isAlbumReviewLikeExists(albumReviewId, loggedInUser.getId());
        Long reviewLikeCount = albumReviewLikeService.countLikesByAlbumReviewId(albumReviewId);

        AlbumReviewLikeResDto dto = AlbumReviewLikeResDto.builder()
                .likeStatus(albumReviewLikeExists ? AlbumReviewLikeToggleResStatus.ON : AlbumReviewLikeToggleResStatus.OFF)
                .likeCount(reviewLikeCount)
                .build();
        return ResponseEntity.ok().body(dto);
    }

    @Operation(summary = "앨범 리뷰에 대한 좋아요 상태 조회", description = "사용자는 어떤 앨범 리뷰에 대한 좋아요 상태를 조회할 수 있습니다. 로그인 하지 않은 경우, isUserLoggedIn=NO, likeStatus=NULL")
    @GetMapping("/{albumReviewId}/like/status")
    @OkResponse
    public ResponseEntity<AlbumReviewLikeStatusResDto> getAlbumReviewLikeCount(@LoginUser(require = false) UserEntity loggedInUser, @PathVariable("albumReviewId") Long albumReviewId) {
        boolean isUserLoggedIn = loggedInUser != null;

        Long likeCount = albumReviewLikeService.countLikesByAlbumReviewId(albumReviewId);
        boolean likeExists = isUserLoggedIn && albumReviewLikeService.isAlbumReviewLikeExists(albumReviewId, loggedInUser.getId());

        AlbumReviewLikeToggleStatus likeStatus = isUserLoggedIn ? (likeExists ? AlbumReviewLikeToggleStatus.ON : AlbumReviewLikeToggleStatus.OFF) : AlbumReviewLikeToggleStatus.NULL;
        AlbumReviewLikeStatusResDto dto = AlbumReviewLikeStatusResDto.builder()
                .isUserLoggedIn(isUserLoggedIn ? IsUserLoggedIn.YES : IsUserLoggedIn.NO)
                .likeStatus(likeStatus)
                .likeCount(likeCount)
                .build();
        return ResponseEntity.ok().body(dto);
    }

    @Operation(summary = "특정 유저가 좋아요한 리뷰 가져오기", description = "특정 유저가 좋아요한 리뷰를 가져옵니다.")
    @GetMapping(value = "/like/{userId}")
    public ResponseEntity<List<AlbumReviewInfoDto>> getLikedAlbumReview(
            @LoginUser(require = false) UserEntity loggedInUser, @PathVariable("userId") String userId, @RequestParam("offset") int offset){
        return ResponseEntity.ok().body(albumReviewLikeService.getLikedAlbumReviews(loggedInUser, userId, offset));
    }


}
