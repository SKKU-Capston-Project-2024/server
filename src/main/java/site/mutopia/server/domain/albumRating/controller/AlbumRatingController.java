package site.mutopia.server.domain.albumRating.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.mutopia.server.domain.albumRating.dto.AlbumRatingReqDto;
import site.mutopia.server.domain.albumRating.dto.AlbumRatingResDto;
import site.mutopia.server.domain.albumRating.service.AlbumRatingService;
import site.mutopia.server.domain.auth.annotation.LoginUser;
import site.mutopia.server.domain.user.entity.UserEntity;
import site.mutopia.server.swagger.response.CreatedResponse;
import site.mutopia.server.swagger.response.OkResponse;

@RestController
@RequestMapping("/album")
@RequiredArgsConstructor
@Tag(name = "Album Rating", description = "Album Rating APIs")
public class AlbumRatingController {

    private final AlbumRatingService albumRatingService;

    @PostMapping("/{albumId}/rating")
    @Operation(summary = "Album Rating 매기기", description = "로그인 한 유저가 Album에 대한 Rating을 매기는 API")
    @CreatedResponse
    public ResponseEntity<Void> saveAlbumRating(@PathVariable("albumId") String albumId, @LoginUser UserEntity loggedInUser, @RequestBody AlbumRatingReqDto dto) {
        albumRatingService.saveAlbumRating(albumId, loggedInUser.getId(), dto.getRating());
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{albumId}/rating")
    @Operation(summary = "Album Rating 수정하기", description = "로그인 한 유저가 Album에 대한 Rating을 수정하는 API")
    @OkResponse
    public ResponseEntity<Void> modifyAlbumRating(@PathVariable("albumId") String albumId, @LoginUser UserEntity loggedInUser, @RequestBody AlbumRatingReqDto dto) {
        albumRatingService.modifyAlbumRating(albumId, loggedInUser.getId(), dto.getRating());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{albumId}/rating")
    @Operation(summary = "Album Rating 조회하기", description = "로그인 한 유저가 Album에 대한 Rating을 조회하는 API")
    @OkResponse
    public ResponseEntity<AlbumRatingResDto> getAlbumRating(@PathVariable("albumId") String albumId, @LoginUser UserEntity loggedInUser) {
        Integer albumRating = albumRatingService.getAlbumRating(loggedInUser.getId(), albumId);
        return ResponseEntity.ok().body(AlbumRatingResDto.builder().rating(albumRating).build());
    }
}
