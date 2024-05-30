package site.mutopia.server.domain.songComment.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.mutopia.server.domain.auth.annotation.LoginUser;
import site.mutopia.server.domain.songComment.dto.SongCommentInfoResDto;
import site.mutopia.server.domain.songComment.dto.SongCommentReqDto;
import site.mutopia.server.domain.songComment.entity.SongCommentEntity;
import site.mutopia.server.domain.songComment.service.SongCommentService;
import site.mutopia.server.domain.user.entity.UserEntity;
import site.mutopia.server.swagger.response.CreatedResponse;
import site.mutopia.server.swagger.response.NotFoundResponse;
import site.mutopia.server.swagger.response.OkResponse;

import java.net.URI;
import java.util.List;


@RequiredArgsConstructor
@RestController
@Tag(name = "Song Comment", description = "곡 한줄평")
public class SongCommentController {

    private final SongCommentService songCommentService;


    @NotFoundResponse
    @OkResponse
    @Operation(summary = "노래 한줄평 조회", description = "노래에 대한 한줄평을 조회합니다.")
    @GetMapping("/user/{userId}/song/{songId}/comment/recent")
    public ResponseEntity<SongCommentInfoResDto> getSongComment(
            @LoginUser(require = false) UserEntity userEntity,
            @PathVariable("userId") String userId,
            @PathVariable("songId") String songId
    ) {
        return ResponseEntity.ok().body(songCommentService.getSongComment(userId, songId, userEntity));
    }

    @CreatedResponse
    @Operation(summary = "곡 한줄평 등록", description = "노래에 대한 한줄평을 생성합니다.")
    @PostMapping("/song/{songId}/comment")
    public ResponseEntity<Void> createSongComment(
            @LoginUser UserEntity userEntity,
            @PathVariable("songId") String songId,
            @RequestBody SongCommentReqDto songCommentReqDto
    ) {
        songCommentService.saveSongComment(userEntity, songId, songCommentReqDto);
        return ResponseEntity.status(201).build();
    }

    @GetMapping("/song/{songId}/comment/recent")
    @Operation(summary = "특정 곡 한줄평 조회", description = "특정 곡에 대한 한줄평을 20개씩 조회합니다. page=1 -> 21~40번째 조회")
    public ResponseEntity<List<SongCommentInfoResDto>> getSongCommentBySong(
            @LoginUser(require = false) UserEntity userEntity,
            @PathVariable("songId") String songId,
            @RequestParam(value = "page", defaultValue = "0") int page){
        return ResponseEntity.ok().body(songCommentService.getSongCommentBySongId(songId, page, userEntity));
    }


    @Operation(summary = "전체 곡 한줄평 조회", description = "전체 사용자가 작성한 곡 한줄평을 최신순으로 조회합니다.")
    @GetMapping("/song/comment/recent")
    public ResponseEntity<List<SongCommentInfoResDto>> getRecentSongComment(
            @LoginUser(require = false) UserEntity userEntity,
            @RequestParam(value = "page", defaultValue = "0") int page){
        return ResponseEntity.ok().body(songCommentService.getRecentSongComment(page, userEntity));
    }

    @Operation(summary = "앨범의 곡 한줄평 조회", description = "특정 앨범의 수록곡에 대한 노래 한줄평을 최신순으로 조회합니다.")
    @GetMapping("/album/{albumId}/song/comment/recent")
    public ResponseEntity<List<SongCommentInfoResDto>> getRecentSongCommentByAlbum(
            @LoginUser(require = false) UserEntity userEntity,
            @PathVariable("albumId") String albumId,
            @RequestParam(value = "page", defaultValue = "0") int page){
        return ResponseEntity.ok().body(songCommentService.getRecentSongCommentByAlbum(albumId, page, userEntity));
    }

    @GetMapping("/user/{userId}/song/comment/recent")
    @Operation(summary = "사용자의 곡 한줄평 조회", description = "특정 사용자가 작성한 노래 한줄평을 최신순으로 조회합니다.")
    public ResponseEntity<List<SongCommentInfoResDto>> getUserSongComments(
            @LoginUser(require = false) UserEntity userEntity,
            @PathVariable("userId") String userId,
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        List<SongCommentInfoResDto> userSongComments = songCommentService.getUserSongComments(userId, page, userEntity);
        return ResponseEntity.ok().body(userSongComments);
    }

}
