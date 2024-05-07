package site.mutopia.server.domain.albumLike.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.mutopia.server.domain.albumLike.service.AlbumLikeService;

@RestController
@RequestMapping("/album")
@RequiredArgsConstructor
public class AlbumLikeController {

    private final AlbumLikeService albumLikeService;

    @PostMapping("/{albumId}/like/toggle")
    public ResponseEntity<?> toggleLike(@PathVariable("albumId") String albumId, @RequestParam("userId") String userId) {
        albumLikeService.toggleAlbumLike(albumId, userId);
        return ResponseEntity.ok().build();
    }
}
