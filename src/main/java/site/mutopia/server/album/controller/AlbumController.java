package site.mutopia.server.album.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import site.mutopia.server.album.dto.response.TrendingAlbumResDto;

@RestController("/album")
@RequiredArgsConstructor
public class AlbumController {
    @GetMapping("/trending")
    public ResponseEntity<TrendingAlbumResDto> trending() {
        return ResponseEntity.ok().body(null);
    }

    @GetMapping("?album_Id={albumId}")
    public ResponseEntity<TrendingAlbumResDto> getAlbum(String albumId) {
        return ResponseEntity.ok().body(null);
    }
}
