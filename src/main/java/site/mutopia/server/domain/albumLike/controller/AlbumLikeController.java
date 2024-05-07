package site.mutopia.server.domain.albumLike.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import site.mutopia.server.domain.albumLike.service.AlbumLikeService;

@RestController
@RequestMapping("/album-like")
@RequiredArgsConstructor
public class AlbumLikeController {

    private final AlbumLikeService albumLikeService;

    @PostMapping("/toggle")
    public ResponseEntity<?> toggleLike(@RequestParam("albumId") String albumId, @RequestParam("userId") String userId) {
        albumLikeService.toggleAlbumLike(albumId, userId);
        return ResponseEntity.ok().build();
    }
}
