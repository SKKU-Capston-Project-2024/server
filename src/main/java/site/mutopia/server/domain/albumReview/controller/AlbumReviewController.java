package site.mutopia.server.domain.albumReview.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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
        return ResponseEntity.status(201).body(AlbumReviewSaveResDto.builder().albumReviewId(albumReviewEntity.getId()).build());
    }

    @Operation(summary = "앨범 리뷰 가져오기", description = "로그인 한 사용자는 각 리뷰에서 자신의 좋아요 여부를 확인할 수 있습니다. review.isLiked 값으로 확인 가능합니다.")
    @GetMapping("/album/review/{albumReviewId}")
    public ResponseEntity<AlbumReviewInfoDto> getAlbumReview(@LoginUser(require = false) UserEntity loggedInUser, @PathVariable("albumReviewId") Long albumReviewId) {
        return ResponseEntity.ok().body(albumReviewService.getAlbumReviewInfoById(loggedInUser,albumReviewId));
    }

    @Operation(summary = "유저가 어떤 앨범에 대해 리뷰를 썼는지 체크하기", description = "로그인 한 사용자는 어떤 앨범에 대해 자신이 리뷰를 썼는지 여부를 체크할 수 있습니다.")
    @GetMapping("/album/{albumId}/review/check")
    public ResponseEntity<AlbumReviewCheckResDto> checkUserHasWrittenReview(@LoginUser UserEntity loggedInUser, @PathVariable("albumId") String albumId) {
        AlbumReviewCheckResDto result = albumReviewService.checkReviewExistence(loggedInUser.getId(), albumId);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/album/review/recent")
    public ResponseEntity<List<AlbumReviewInfoDto>> getRecentAlbumReview(
            @LoginUser(require = false) UserEntity loggedInUser, @RequestParam("offset") int offset){
        return ResponseEntity.ok().body(albumReviewService.getRecentAlbumReview(loggedInUser, offset));
    }

    /*
    @GetMapping("/my")
    public ResponseEntity<AlbumReviewEntity> getMyAlbumReview(@LoginUser UserEntity loggedInUser, @RequestParam("albumId") String albumId) {
        return ResponseEntity.ok().body(albumReviewService.getMyAlbumReview(loggedInUser.getId(), albumId));
    }
    */
}
