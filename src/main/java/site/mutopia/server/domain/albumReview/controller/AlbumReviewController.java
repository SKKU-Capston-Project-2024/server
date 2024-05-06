package site.mutopia.server.domain.albumReview.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.mutopia.server.domain.albumReview.dto.AlbumReviewSaveDto;
import site.mutopia.server.domain.albumReview.service.AlbumReviewService;
import site.mutopia.server.domain.auth.annotation.LoginUser;
import site.mutopia.server.domain.user.entity.UserEntity;

@RestController
@RequestMapping("/album/review")
@RequiredArgsConstructor
public class AlbumReviewController {

    private final AlbumReviewService albumReviewService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> saveAlbumReview(@LoginUser UserEntity loggedInUser, @RequestBody AlbumReviewSaveDto saveDto) {
        albumReviewService.saveAlbumReview(loggedInUser.getUserId(), saveDto);

        // TODO: Response 형식 재정의
        return ResponseEntity.ok("Album review saved successfully");
    }
}
