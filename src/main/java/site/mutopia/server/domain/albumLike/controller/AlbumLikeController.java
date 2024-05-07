package site.mutopia.server.domain.albumLike.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.mutopia.server.domain.albumLike.service.AlbumLikeService;
import site.mutopia.server.domain.auth.annotation.LoginUser;
import site.mutopia.server.domain.user.entity.UserEntity;

@RestController
@RequestMapping("/album")
@RequiredArgsConstructor
public class AlbumLikeController {

    private final AlbumLikeService albumLikeService;

    @PostMapping("/{albumId}/like/toggle")
    public ResponseEntity<?> toggleLike(@PathVariable("albumId") String albumId, @LoginUser UserEntity loggedInUser) {
        albumLikeService.toggleAlbumLike(albumId, loggedInUser.getId());
        return ResponseEntity.ok().build();
    }
}
