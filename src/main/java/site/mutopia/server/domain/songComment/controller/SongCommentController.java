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
@Tag(name = "SongComment", description = "Song Comment APIs")
public class SongCommentController {

    private final SongCommentService songCommentService;


    @NotFoundResponse
    @OkResponse
    @Operation(summary = "노래 한줄평 조회", description = "노래에 대한 한줄평을 조회합니다.")
    @GetMapping("/user/{userId}/song/{songId}/comment")
    public ResponseEntity<SongCommentInfoResDto> getSongComment(
            @PathVariable("userId") String userId,
            @PathVariable("songId") String songId
    ) {
        return ResponseEntity.ok().body(songCommentService.getSongComment(userId, songId));
    }

    @CreatedResponse
    @Operation(summary = "노래 한줄평 등록", description = "노래에 대한 한줄평을 생성합니다.")
    @PostMapping("/song/{songId}/comment")
    public ResponseEntity<SongCommentEntity> createSongComment(
            @LoginUser UserEntity userEntity,
            @PathVariable("songId") String songId,
            @RequestBody SongCommentReqDto songCommentReqDto
    ) {
        return ResponseEntity.status(201).body(songCommentService.saveSongComment(userEntity, songId, songCommentReqDto));
    }

    @GetMapping("/song/comment/recent")
    public ResponseEntity<List<SongCommentInfoResDto>> getRecentSongComment(
            @RequestParam(value = "page", defaultValue = "0") int page){
        return ResponseEntity.ok().body(songCommentService.getRecentSongComment(page));
    }

    @GetMapping("/album/{albumId}/song/comment/recent")
    public ResponseEntity<List<SongCommentInfoResDto>> getRecentSongCommentByAlbum(
            @PathVariable("albumId") String albumId,
            @RequestParam(value = "page", defaultValue = "0") int page){
        return ResponseEntity.ok().body(songCommentService.getRecentSongCommentByAlbum(albumId, page));
    }


}
