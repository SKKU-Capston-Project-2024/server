package site.mutopia.server.domain.album.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import site.mutopia.server.domain.album.domain.MutopiaAlbum;
import site.mutopia.server.domain.album.dto.response.TrendingAlbumResDto;
import site.mutopia.server.domain.album.service.AlbumService;

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

    @GetMapping("?album_Id={albumId}")
    public ResponseEntity<TrendingAlbumResDto> getAlbum(String albumId) {
        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/search")
    public ResponseEntity<List<MutopiaAlbum>> searchAlbum(@RequestParam String keyword) {
        return ResponseEntity.ok().body(albumService.searchAlbumByKeyword(keyword));
    }

}
