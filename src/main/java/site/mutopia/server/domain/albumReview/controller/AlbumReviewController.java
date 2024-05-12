package site.mutopia.server.domain.albumReview.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.mutopia.server.domain.albumReview.dto.AlbumReviewInfoDto;
import site.mutopia.server.domain.albumReview.dto.AlbumReviewSaveReqDto;
import site.mutopia.server.domain.albumReview.dto.AlbumReviewSaveResDto;
import site.mutopia.server.domain.albumReview.entity.AlbumReviewEntity;
import site.mutopia.server.domain.albumReview.service.AlbumReviewService;
import site.mutopia.server.domain.auth.annotation.LoginUser;
import site.mutopia.server.domain.user.entity.UserEntity;
import site.mutopia.server.swagger.response.CreatedResponse;

@RestController
@RequestMapping("/album/review")
@RequiredArgsConstructor
@Tag(name = "Album Review", description = "Album Review APIs")
public class AlbumReviewController {

    private final AlbumReviewService albumReviewService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CreatedResponse
    public ResponseEntity<AlbumReviewSaveResDto> saveAlbumReview(@LoginUser UserEntity loggedInUser, @RequestBody AlbumReviewSaveReqDto saveDto) {
        AlbumReviewEntity albumReviewEntity = albumReviewService.saveAlbumReview(loggedInUser.getId(), saveDto);
        return ResponseEntity.ok().body(AlbumReviewSaveResDto.builder().albumReviewId(albumReviewEntity.getId()).build());
    }

    @Operation(summary = "리뷰 가져오기", description = "로그인 한 사용자는 각 리뷰에서 자신의 좋아요 여부를 확인할 수 있습니다. review.isLiked 값으로 확인 가능합니다.")
    @GetMapping("/{albumReviewId}")
    public ResponseEntity<AlbumReviewInfoDto> getAlbumReview(@LoginUser(require = false) UserEntity loggedInUser, @PathVariable("albumReviewId") Long albumReviewId) {
        return ResponseEntity.ok().body(albumReviewService.getAlbumReviewInfoById(loggedInUser,albumReviewId));
    }

    /*@GetMapping("/my")
    public ResponseEntity<AlbumReviewEntity> getMyAlbumReview(@LoginUser UserEntity loggedInUser, @RequestParam("albumId") String albumId) {
        return ResponseEntity.ok().body(albumReviewService.getMyAlbumReview(loggedInUser.getId(), albumId));
    }*/


}
