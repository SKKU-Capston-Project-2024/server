package site.mutopia.server.domain.album.controller;

import io.swagger.v3.oas.annotations.media.ExampleObject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.mutopia.server.domain.album.dto.response.AlbumDetailResDto;
import site.mutopia.server.domain.album.entity.AlbumEntity;
import site.mutopia.server.domain.album.dto.response.TrendingAlbumResDto;
import site.mutopia.server.domain.album.service.AlbumService;
import site.mutopia.server.domain.auth.annotation.LoginUser;
import site.mutopia.server.swagger.response.NotFoundResponse;
import site.mutopia.server.swagger.response.OkResponse;

import java.util.List;

@RestController
@RequestMapping("/album")
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
            @PathVariable("albumId") String albumId) {
        return ResponseEntity.ok().body(albumService.findAlbumById(albumId));
    }


    @GetMapping("/search")
    public ResponseEntity<List<AlbumEntity>> searchAlbum(@RequestParam(value = "keyword") String keyword) {
        return ResponseEntity.ok().body(albumService.searchAlbumByKeyword(keyword));
    }


}
