package site.mutopia.server.domain.album.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.mutopia.server.domain.album.dto.response.AlbumDetailResDto;
import site.mutopia.server.domain.album.dto.response.TrendingAlbumResDto;
import site.mutopia.server.domain.album.entity.AlbumEntity;
import site.mutopia.server.domain.album.service.AlbumService;
import site.mutopia.server.domain.albumReview.dto.AlbumReviewInfoDto;
import site.mutopia.server.domain.albumReview.service.AlbumReviewService;
import site.mutopia.server.domain.auth.annotation.LoginUser;
import site.mutopia.server.domain.user.entity.UserEntity;
import site.mutopia.server.swagger.response.NotFoundResponse;
import site.mutopia.server.swagger.response.OkResponse;

import java.util.List;

@RestController
@RequestMapping("/album")
@Tag(name = "Album", description = "앨범 관련 API")
@RequiredArgsConstructor
public class AlbumController {

    private final AlbumService albumService;
    private final AlbumReviewService albumReviewService;

    @GetMapping("/trending")
    public ResponseEntity<TrendingAlbumResDto> trending() {
        return ResponseEntity.ok().body(null);
    }

    @NotFoundResponse
    @OkResponse
    @GetMapping("/info/{albumId}")
    public ResponseEntity<AlbumDetailResDto> getAlbum(
            @PathVariable(value = "albumId") String albumId) {
        return ResponseEntity.ok().body(albumService.findAlbumById(albumId));
    }

    @Operation(summary = "앨범 검색", description = "keyword로 검색, offset으로 페이징 가장 관련도 높은 순으로 정렬")
    @GetMapping("/search")
    public ResponseEntity<List<AlbumEntity>> searchAlbum(
            @RequestParam(value = "keyword") String keyword,
            @RequestParam(value = "offset", required = false, defaultValue = "0") int offset){
        return ResponseEntity.ok().body(albumService.searchAlbumByKeyword(keyword, offset));
    }

    @Operation(summary = "앨범의 최근 리뷰 조회", description = "앨범의 최근 리뷰를 조회합니다. 한번에 20개씩 가져옵니다. offset으로 페이징")
    @GetMapping(value = "/{albumId}/review/recent")
    public ResponseEntity<List<AlbumReviewInfoDto>> getRecentAlbumReviews(
            @LoginUser(require = false) UserEntity userEntity,
            @PathVariable("albumId") String albumId, @RequestParam(value = "offset", required = false, defaultValue = "0") int offset){
        return ResponseEntity.ok().body(albumReviewService.getRecentAlbumReviews(userEntity,albumId,offset));
    }

    @Operation(summary = "앨범의 인기 리뷰 조회", description = "앨범의 인기 리뷰를 조회합니다. 한번에 20개씩 가져옵니다. offset으로 페이징")
    @GetMapping(value ="/{albumId}/review/popular")
    public ResponseEntity<List<AlbumReviewInfoDto>> getPopularAlbumReviews(
            @LoginUser(require = false) UserEntity userEntity,
            @PathVariable("albumId") String albumId, @RequestParam(value = "offset", required = false, defaultValue = "0") int offset){
        return ResponseEntity.ok().body(albumReviewService.getPopularAlbumReviewsByAlbumId(userEntity,albumId,offset));
    }

}
