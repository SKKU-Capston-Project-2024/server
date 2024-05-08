package site.mutopia.server.domain.albumRating.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.mutopia.server.domain.albumRating.dto.AlbumRatingSaveReqDto;
import site.mutopia.server.domain.albumRating.service.AlbumRatingService;
import site.mutopia.server.domain.auth.annotation.LoginUser;
import site.mutopia.server.domain.user.entity.UserEntity;
import site.mutopia.server.swagger.response.CreatedResponse;

@RestController
@RequestMapping("/album")
@RequiredArgsConstructor
@Tag(name = "Album Rating", description = "Album Rating APIs")
public class AlbumRatingController {

    private final AlbumRatingService albumRatingService;

    @PostMapping("/{albumId}/rating")
    @Operation(summary = "Album Rating 매기기", description = "로그인 한 유저가 Album에 대한 Rating을 매기는 API")
    @CreatedResponse
    public ResponseEntity<Void> toggleLike(@PathVariable("albumId") String albumId, @LoginUser UserEntity loggedInUser, @RequestBody AlbumRatingSaveReqDto dto) {
        albumRatingService.saveAlbumRating(albumId, loggedInUser.getId(), dto.getRating());
        return ResponseEntity.ok().build();
    }
}
