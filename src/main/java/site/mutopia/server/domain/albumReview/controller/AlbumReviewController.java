package site.mutopia.server.domain.albumReview.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.mutopia.server.domain.albumReview.dto.AlbumReviewCheckResDto;
import site.mutopia.server.domain.albumReview.dto.AlbumReviewInfoDto;
import site.mutopia.server.domain.albumReview.dto.AlbumReviewSaveReqDto;
import site.mutopia.server.domain.albumReview.dto.AlbumReviewSaveResDto;
import site.mutopia.server.domain.albumReview.entity.AlbumReviewEntity;
import site.mutopia.server.domain.albumReview.service.AlbumReviewService;
import site.mutopia.server.domain.auth.annotation.LoginUser;
import site.mutopia.server.domain.user.entity.UserEntity;
import site.mutopia.server.swagger.response.CreatedResponse;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Tag(name = "Album Review", description = "Album Review APIs")
public class AlbumReviewController {

    private final AlbumReviewService albumReviewService;

    @Operation(summary = "앨범 리뷰 저장하기", description = "로그인 한 사용자는 앨범에 대한 리뷰를 작성할 수 있습니다.")
    @PostMapping("/album/review")
    @CreatedResponse
    public ResponseEntity<AlbumReviewSaveResDto> saveAlbumReview(@LoginUser UserEntity loggedInUser, @RequestBody AlbumReviewSaveReqDto saveDto) {
        AlbumReviewEntity albumReviewEntity = albumReviewService.saveAlbumReview(loggedInUser.getId(), saveDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(AlbumReviewSaveResDto.builder().albumReviewId(albumReviewEntity.getId()).build());
    }

    @Operation(summary = "앨범 리뷰 가져오기", description = "로그인 한 사용자는 각 리뷰에서 자신의 좋아요 여부를 확인할 수 있습니다. review.isLiked 값으로 확인 가능합니다.")
    @GetMapping("/album/review/{albumReviewId}")
    public ResponseEntity<AlbumReviewInfoDto> getAlbumReview(@LoginUser(require = false) UserEntity loggedInUser, @PathVariable("albumReviewId") Long albumReviewId) {
        return ResponseEntity.ok().body(albumReviewService.getAlbumReviewInfoById(loggedInUser,albumReviewId));
    }

    @Operation(summary = "앨범 리뷰 제거하기", description = "로그인 한 사용자는 자신의 리뷰를 삭제할 수 있다.")
    @DeleteMapping("/album/review/{albumReviewId}")
    public ResponseEntity<Void> deleteAlbumReview(@LoginUser UserEntity loggedInUser, @PathVariable("albumReviewId") Long albumReviewId) {
        // TODO: loggedInUser가 해당 리뷰를 썻는지 체크 하는 로직

        albumReviewService.deleteAlbumReviewById(albumReviewId);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "유저가 어떤 앨범에 대해 리뷰를 썼는지 체크하기", description = "로그인 한 사용자는 어떤 앨범에 대해 자신이 리뷰를 썼는지 여부를 체크할 수 있습니다.")
    @GetMapping("/album/{albumId}/review/check")
    public ResponseEntity<AlbumReviewCheckResDto> checkUserHasWrittenReview(@LoginUser UserEntity loggedInUser, @PathVariable("albumId") String albumId) {
        AlbumReviewCheckResDto result = albumReviewService.checkReviewExistence(loggedInUser.getId(), albumId);
        return ResponseEntity.ok().body(result);
    }

    @Operation(summary = "전체 리뷰 최신순으로 가져오기", description = "최신순으로 정렬된 리뷰를 offset 부터 20개 가져옵니다.")
    @GetMapping(value = "/album/review/recent")
    public ResponseEntity<List<AlbumReviewInfoDto>> getRecentAlbumReview(
            @LoginUser(require = false) UserEntity loggedInUser, @RequestParam("offset") int offset){
        return ResponseEntity.ok().body(albumReviewService.getRecentAlbumReview(loggedInUser, offset));
    }

    @Operation(summary = "전체 리뷰 좋아요순으로 가져오기", description = "좋아요순으로 정렬된 리뷰를 offset 부터 20개 가져옵니다.")
    @GetMapping(value = "/album/review/popular")
    public ResponseEntity<List<AlbumReviewInfoDto>> getPopularAlbumReview(
            @LoginUser(require = false) UserEntity loggedInUser, @RequestParam("offset") int offset){
        return ResponseEntity.ok().body(albumReviewService.getPopularAlbumReviews(loggedInUser, offset));
    }

    @Operation(summary = "팔로잉의 최신 리뷰 조회", description = "팔로워들의 최신 리뷰를 20개씩 가져옵니다. 페이지는 0번부터 시작")
    @GetMapping(value = "/user/following/review/recent")
    public ResponseEntity<List<AlbumReviewInfoDto>> getFollowingRecentAlbumReview(
            @LoginUser UserEntity loggedInUser, @RequestParam("page") int page){
        return ResponseEntity.ok().body(albumReviewService.getFollowingRecentAlbumReview(loggedInUser, page));
    }

    @Operation(summary= "앨범 리뷰 수정하기", description = "로그인 한 사용자는 자신의 리뷰를 수정할 수 있습니다.")
    @PutMapping("/album/review/{albumReviewId}")
    public ResponseEntity<Void> modifyAlbumReview(@LoginUser UserEntity loggedInUser, @PathVariable("albumReviewId") Long albumReviewId, @RequestBody AlbumReviewSaveReqDto saveDto){
        albumReviewService.modifyAlbumReview(loggedInUser.getId(), albumReviewId, saveDto);
        return ResponseEntity.noContent().build();
    }

}
