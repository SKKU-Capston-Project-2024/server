package site.mutopia.server.domain.song.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import site.mutopia.server.domain.auth.annotation.LoginUser;
import site.mutopia.server.domain.song.dto.SongInfoDto;
import site.mutopia.server.domain.song.dto.SongSearchResDto;
import site.mutopia.server.domain.song.service.SongService;
import site.mutopia.server.domain.user.entity.UserEntity;
import site.mutopia.server.spotify.dto.track.SearchTracksDto;

import java.util.List;


@RestController
@RequiredArgsConstructor
@Tag(name = "Song", description = "곡 관련 API")
@RequestMapping("/song")
public class SongController {

    private final SongService songService;

    @Operation(summary = "곡 검색", description = "키워드로 곡을 검색합니다.")
    @GetMapping("/search")
    public ResponseEntity<List<SongSearchResDto>> search(
            @RequestParam("keyword") String keyword,
            @RequestParam("offset") int offset
    )
    {
        return ResponseEntity.ok().body(songService.search(keyword, offset));
    }

    @Operation(summary = "곡 정보 조회", description = "곡 정보를 조회합니다.")
    @GetMapping("/info/{songId}")
    public ResponseEntity<SongInfoDto> getSong(
            @LoginUser(require = false) UserEntity user,
            @PathVariable("songId") String songId
    )
    {
        return ResponseEntity.ok().body(songService.getSong(user, songId));
    }


}
