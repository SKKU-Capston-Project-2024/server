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
import site.mutopia.server.swagger.response.NotFoundResponse;
import site.mutopia.server.swagger.response.OkResponse;

import java.util.List;

@RestController
@RequestMapping("/album")
@Tag(name = "Album", description = "앨범 관련 API")
@RequiredArgsConstructor
public class AlbumController {

    private final AlbumService albumService;

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
}
