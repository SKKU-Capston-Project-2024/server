package site.mutopia.server.domain.songCommentLike.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.mutopia.server.domain.songCommentLike.dto.LikeSongCommentDto;
import site.mutopia.server.domain.songCommentLike.dto.SongCommentLikeStatusResDto;
import site.mutopia.server.domain.songCommentLike.dto.SongCommentLikeStatusResDto.SongCommentLikeToggleStatus;
import site.mutopia.server.domain.songCommentLike.dto.SongCommentLikeStatusResDto.IsUserLoggedIn;
import site.mutopia.server.domain.songCommentLike.dto.SongCommentLikeToggleResDto;
import site.mutopia.server.domain.songCommentLike.dto.SongCommentLikeToggleResDto.SongCommentLikeToggleResStatus;
import site.mutopia.server.domain.songCommentLike.service.SongCommentLikeService;
import site.mutopia.server.domain.auth.annotation.LoginUser;
import site.mutopia.server.domain.user.entity.UserEntity;
import site.mutopia.server.swagger.response.OkResponse;

import java.util.List;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
@Tag(name = "Song Like", description = "Song Like APIs")
public class SongCommentLikeController {

/*
    private final SongCommentLikeService songCommentLikeService;

    @PostMapping("song/{songId}/comment/{writerId}/like/toggle")
    @Operation(summary = "곡 한줄평의 좋아요 버튼 토글하기", description = "로그인 한 사용자는 곡 한줄평의 좋아요 버튼을 토글할 수 있다.")
    @OkResponse
    public ResponseEntity<Void> toggleLikeToSong(@PathVariable("songId") String songId, @PathVariable("writerId") String writerId, @LoginUser UserEntity loggedInUser) {
        songCommentLikeService.toggleSongCommentLike(songId, writerId, loggedInUser);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "특정 유저가 좋아요 한 곡 한줄평 가져오기", description = "특정 유저가 좋아요한 곡 한줄평을 가져옵니다.")
    @GetMapping("/like/{userId}")
    public ResponseEntity<List<LikeSongCommentDto>> getLikedSongCommentByUser(@PathVariable("userId") String userId, @RequestParam("page") int page) {
        return ResponseEntity.ok().body(songCommentLikeService.getLikedSongsByUser(userId, page));
    }
*/


}
