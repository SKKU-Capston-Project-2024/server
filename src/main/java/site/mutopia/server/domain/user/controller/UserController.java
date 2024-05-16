package site.mutopia.server.domain.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.mutopia.server.domain.albumReview.dto.AlbumReviewInfoDto;
import site.mutopia.server.domain.albumReview.service.AlbumReviewService;
import site.mutopia.server.domain.auth.annotation.LoginUser;
import site.mutopia.server.domain.playlist.dto.PlaylistInfoDto;
import site.mutopia.server.domain.playlist.service.PlaylistService;
import site.mutopia.server.domain.songComment.dto.SongCommentInfoResDto;
import site.mutopia.server.domain.songComment.service.SongCommentService;
import site.mutopia.server.domain.topster.dto.TopsterInfoDto;
import site.mutopia.server.domain.topster.service.TopsterService;
import site.mutopia.server.domain.user.entity.UserEntity;
import site.mutopia.server.swagger.response.NotFoundResponse;
import site.mutopia.server.swagger.response.OkResponse;

import java.util.List;

@RestController
@Tag(name = "User", description = "User APIs")
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final TopsterService topsterService;
    private final AlbumReviewService albumReviewService;
    private final SongCommentService songCommentService;
    private final PlaylistService playlistService;

    @GetMapping("/info")
    public ResponseEntity<UserEntity> getUserInfo(@LoginUser UserEntity userEntity) {
        return ResponseEntity.ok().body(userEntity);
    }


    @Operation(summary = "유저 정보 가져오기", description = "유저의 닉네임, 프로필 사진,리뷰, 좋아요, 팔로워, 팔로잉 수를 가져옵니다.")
    @NotFoundResponse
    @OkResponse
    @GetMapping("/{userId}/profile/aggregation")
    public ResponseEntity<UserAggregationInfoResDto> getUserAggregationInfo(@PathVariable("userId") String userId) {
        UserAggregationInfoResDto result = userService.aggregateUserInfo(userId);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/{userId}/topster")
    @NotFoundResponse
    @OkResponse
    public ResponseEntity<TopsterInfoDto> getUserTopster(@PathVariable("userId") String userId) {
        TopsterInfoDto topster = topsterService.getTopsterInfoByUserId(userId);
        return ResponseEntity.ok().body(topster);
    }

    @Operation(summary = "리뷰 가져오기", description = "로그인 한 사용자는 각 리뷰에서 자신의 좋아요 여부를 확인할 수 있습니다. review.isLiked 값으로 확인 가능합니다.")
    @GetMapping("/{userId}/album/review")
    public ResponseEntity<List<AlbumReviewInfoDto>> getUserAlbumReviews(
            @LoginUser(require = false) UserEntity loggedInUser,
            @PathVariable("userId") String userId,
            @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit) {
        List<AlbumReviewInfoDto> reviews = albumReviewService.findAlbumReviewInfoDtoListByUserId(loggedInUser, userId, limit);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/{userId}/song/comment")
    public ResponseEntity<List<SongCommentInfoResDto>> getUserSongComments(@PathVariable("userId") String userId,
                                                 @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit) {
        List<SongCommentInfoResDto> userSongComments = songCommentService.getUserSongComments(userId, limit);
        return ResponseEntity.ok().body(userSongComments);
    }

    // TODO: Playlist 기능 구현하고 Test 하기
    @GetMapping("/{userId}/playlist")
    public ResponseEntity<List<?>> getUserPlaylist(@PathVariable("userId") String userId,
                                                   @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit) {
        List<PlaylistInfoDto> userPlaylists = playlistService.getUserPlaylists(userId, limit);
        return ResponseEntity.ok().body(userPlaylists);
    }
}
