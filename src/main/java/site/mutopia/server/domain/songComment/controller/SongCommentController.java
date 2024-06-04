package site.mutopia.server.domain.songComment.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.mutopia.server.domain.auth.annotation.LoginUser;
import site.mutopia.server.domain.songComment.dto.SongCommentInfoResDto;
import site.mutopia.server.domain.songComment.dto.OrderBy;
import site.mutopia.server.domain.songComment.dto.SongCommentReqDto;
import site.mutopia.server.domain.songComment.service.SongCommentService;
import site.mutopia.server.domain.user.entity.UserEntity;
import site.mutopia.server.swagger.response.CreatedResponse;
import site.mutopia.server.swagger.response.NotFoundResponse;
import site.mutopia.server.swagger.response.OkResponse;

import java.util.List;


@RequiredArgsConstructor
@RestController
@Tag(name = "Song Comment", description = "곡 한줄평")
public class SongCommentController {

    private final SongCommentService songCommentService;


    @NotFoundResponse
    @OkResponse
    @Operation(summary = "노래 한줄평 조회", description = "특정 유저의 특정 곡에 대한 한줄평을 조회합니다.")
    @GetMapping("/user/{userId}/song/{songId}/comment/recent")
    public ResponseEntity<SongCommentInfoResDto> getSongComment(
            @LoginUser(require = false) UserEntity userEntity,
            @PathVariable("userId") String userId,
            @PathVariable("songId") String songId
    ) {
        return ResponseEntity.ok().body(songCommentService.getSongComments(userId, songId, userEntity));
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
    @Operation(summary = "특정 곡 한줄평 조회 (최근순 조회)", description = "특정 곡에 대한 한줄평을 20개씩 조회합니다. page=1 -> 21~40번째 조회")
    public ResponseEntity<List<SongCommentInfoResDto>> getSongCommentBySong(
            @LoginUser(require = false) UserEntity userEntity,
            @PathVariable("songId") String songId,
            @RequestParam(value = "page", defaultValue = "0") int page){
        return ResponseEntity.ok().body(songCommentService.getSongCommentBySongId(songId, page, userEntity, OrderBy.RECENT));
    }

    @GetMapping("/song/{songId}/comment/popular")
    @Operation(summary = "특정 곡 한줄평 조회 (인기순 조회)", description = "특정 곡에 대한 한줄평을 20개씩 조회합니다. page=1 -> 21~40번째 조회")
    public ResponseEntity<List<SongCommentInfoResDto>> getSongCommentBySongIdOrderByPopular(
            @LoginUser(require = false) UserEntity userEntity,
            @PathVariable("songId") String songId,
            @RequestParam(value = "page", defaultValue = "0") int page){
        return ResponseEntity.ok().body(songCommentService.getSongCommentBySongId(songId, page, userEntity, OrderBy.POPULAR));
    }

    @Operation(summary = "전체 곡 한줄평 조회 (최신순)", description = "전체 사용자가 작성한 곡 한줄평을 최신순으로 조회합니다.")
    @GetMapping("/song/comment/recent")
    public ResponseEntity<List<SongCommentInfoResDto>> getRecentSongComment(
            @LoginUser(require = false) UserEntity userEntity,
            @RequestParam(value = "page", defaultValue = "0") int page){
        return ResponseEntity.ok().body(songCommentService.getSongComments(page, userEntity, OrderBy.RECENT));
    }

    @Operation(summary = "전체 곡 한줄평 조회 (인기순)", description = "전체 사용자가 작성한 곡 한줄평을 최신순으로 조회합니다.")
    @GetMapping("/song/comment/popular")
    public ResponseEntity<List<SongCommentInfoResDto>> getPopularSongComment(
            @LoginUser(require = false) UserEntity userEntity,
            @RequestParam(value = "page", defaultValue = "0") int page){
        return ResponseEntity.ok().body(songCommentService.getSongComments(page, userEntity, OrderBy.POPULAR));
    }

    @Operation(summary = "앨범의 곡 한줄평 조회 (최근순 조회)", description = "특정 앨범의 수록곡에 대한 노래 한줄평을 최신순으로 조회합니다.")
    @GetMapping("/album/{albumId}/song/comment/recent")
    public ResponseEntity<List<SongCommentInfoResDto>> getRecentSongCommentsByAlbum(
            @LoginUser(require = false) UserEntity userEntity,
            @PathVariable("albumId") String albumId,
            @RequestParam(value = "page", defaultValue = "0") int page){
        return ResponseEntity.ok().body(songCommentService.getSongCommentsByAlbumId(albumId, page, userEntity, OrderBy.RECENT));
    }

    @Operation(summary = "앨범의 곡 한줄평 조회 (인기순 조회)", description = "특정 앨범의 수록곡에 대한 노래 한줄평을 인기순으로 조회합니다.")
    @GetMapping("/album/{albumId}/song/comment/popular")
    public ResponseEntity<List<SongCommentInfoResDto>> getPopularSongCommentsByAlbum(
            @LoginUser(require = false) UserEntity userEntity,
            @PathVariable("albumId") String albumId,
            @RequestParam(value = "page", defaultValue = "0") int page){
        return ResponseEntity.ok().body(songCommentService.getSongCommentsByAlbumId(albumId, page, userEntity, OrderBy.POPULAR));
    }

    @GetMapping("/user/{userId}/song/comment/recent")
    @Operation(summary = "사용자의 곡 한줄평 조회 (최근순 조회)", description = "특정 사용자가 작성한 노래 한줄평을 최신순으로 조회합니다.")
    public ResponseEntity<List<SongCommentInfoResDto>> getRecentUserSongComments(
            @LoginUser(require = false) UserEntity userEntity,
            @PathVariable("userId") String userId,
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        List<SongCommentInfoResDto> userSongComments = songCommentService.getUserSongComments(userId, page, userEntity, OrderBy.RECENT);
        return ResponseEntity.ok().body(userSongComments);
    }

    @GetMapping("/user/{userId}/song/comment/popular")
    @Operation(summary = "사용자의 곡 한줄평 조회 (인기순 조회)", description = "특정 사용자가 작성한 노래 한줄평을 인기순으로 조회합니다.")
    public ResponseEntity<List<SongCommentInfoResDto>> getPopularUserSongComments(
            @LoginUser(require = false) UserEntity userEntity,
            @PathVariable("userId") String userId,
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        List<SongCommentInfoResDto> userSongComments = songCommentService.getUserSongComments(userId, page, userEntity, OrderBy.POPULAR);
        return ResponseEntity.ok().body(userSongComments);
    }

    @DeleteMapping("/song/{songId}/comment")
    @Operation(summary = "곡 한줄평 삭제", description = "특정 곡에 대한 한줄평을 삭제합니다.")
    public ResponseEntity<Void> deleteSongComment(
            @LoginUser UserEntity userEntity,
            @PathVariable("songId") String songId
    ) {
        songCommentService.deleteSongComment(songId, userEntity.getId());
        return ResponseEntity.noContent().build();
    }

}
