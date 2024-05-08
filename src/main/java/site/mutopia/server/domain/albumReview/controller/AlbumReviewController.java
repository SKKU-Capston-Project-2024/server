package site.mutopia.server.domain.albumReview.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.mutopia.server.domain.albumReview.dto.AlbumReviewInfoDto;
import site.mutopia.server.domain.albumReview.dto.AlbumReviewSaveDto;
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
    public ResponseEntity<Void> saveAlbumReview(@LoginUser UserEntity loggedInUser, @RequestBody AlbumReviewSaveDto saveDto) {
        albumReviewService.saveAlbumReview(loggedInUser.getId(), saveDto);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{albumReviewId}")
    public ResponseEntity<AlbumReviewInfoDto> getAlbumReview(@PathVariable("albumReviewId") Long albumReviewId) {
        return ResponseEntity.ok().body(albumReviewService.getAlbumReviewInfoById(albumReviewId));
    }
}
