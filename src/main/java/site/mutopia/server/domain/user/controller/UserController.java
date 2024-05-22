package site.mutopia.server.domain.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.mutopia.server.domain.albumReview.dto.AlbumReviewInfoDto;
import site.mutopia.server.domain.albumReview.service.AlbumReviewService;
import site.mutopia.server.domain.auth.annotation.LoginUser;
import site.mutopia.server.domain.songComment.dto.SongCommentInfoResDto;
import site.mutopia.server.domain.songComment.service.SongCommentService;
import site.mutopia.server.domain.user.entity.UserEntity;

import java.util.List;

@RestController
@Tag(name = "User", description = "User APIs")
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final AlbumReviewService albumReviewService;
    private final SongCommentService songCommentService;

    @GetMapping("/info")
    public ResponseEntity<UserEntity> getUserInfo(@LoginUser UserEntity userEntity) {
        return ResponseEntity.ok().body(userEntity);
    }

    @Operation(summary = "특정 유저의 최신 리뷰 가져오기", description = "로그인 한 사용자는 각 리뷰에서 자신의 좋아요 여부를 확인할 수 있습니다. review.isLiked 값으로 확인 가능합니다.")
    @GetMapping("/{userId}/album/review/recent")
    public ResponseEntity<List<AlbumReviewInfoDto>> getUserAlbumReviews(
            @LoginUser(require = false) UserEntity loggedInUser,
            @PathVariable("userId") String userId,
            @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset) {
        List<AlbumReviewInfoDto> reviews = albumReviewService.findAlbumReviewInfoDtoListByUserId(loggedInUser, userId, offset);
        return ResponseEntity.ok(reviews);
    }

    @Operation(summary = "특정 유저의 앨범 리뷰 좋아요순으로 가져오기", description = "로그인 한 사용자는 각 리뷰에서 자신의 좋아요 여부를 확인할 수 있습니다. review.isLiked 값으로 확인 가능합니다.")
    @GetMapping("/{userId}/album/review/popular")
    public ResponseEntity<List<AlbumReviewInfoDto>> getUserAlbumReviewsOrderByLike(
            @LoginUser(require = false) UserEntity loggedInUser,
            @PathVariable("userId") String userId,
            @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset) {
        List<AlbumReviewInfoDto> reviews = albumReviewService.findByUserIdOrderByLike(userId, offset, loggedInUser == null ? null : loggedInUser.getId());
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/{userId}/song/comment")
    public ResponseEntity<List<SongCommentInfoResDto>> getUserSongComments(@PathVariable("userId") String userId,
                                                 @RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        List<SongCommentInfoResDto> userSongComments = songCommentService.getUserSongComments(userId, page);
        return ResponseEntity.ok().body(userSongComments);
    }

}
