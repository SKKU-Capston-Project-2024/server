package site.mutopia.server.domain.topster.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.mutopia.server.domain.auth.annotation.LoginUser;
import site.mutopia.server.domain.topster.dto.*;
import site.mutopia.server.domain.topster.entity.TopsterEntity;
import site.mutopia.server.domain.topster.service.TopsterService;
import site.mutopia.server.domain.user.entity.UserEntity;
import site.mutopia.server.swagger.response.CreatedResponse;
import site.mutopia.server.swagger.response.OkResponse;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Tag(name = "Topster", description = "Topster APIs")
public class TopsterController {

    private final TopsterService topsterService;

    @Operation(summary = "탑스터 저장", description = "로그인한 유저는 탑스터를 저장할 수 있다.")
    @PostMapping("/user/profile/topster")
    @ResponseStatus(HttpStatus.CREATED)
    @CreatedResponse
    public ResponseEntity<TopsterSaveResDto> saveTopster(@LoginUser UserEntity loggedInUser, @RequestBody TopsterSaveReqDto saveDto) {
        TopsterEntity savedTopster = topsterService.saveTopster(loggedInUser.getId(), saveDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(TopsterSaveResDto.builder().topsterId(savedTopster.getId()).build());
    }

    @Operation(summary = "탑스터 조회", description = "유저는 topsterId로 탑스터를 조회할 수 있다.")
    @GetMapping("/user/{userId}/profile/topster")
    @OkResponse
    public ResponseEntity<TopsterInfoDto> getTopsterByUserId(@PathVariable("userId") String userId) {
        TopsterInfoDto topsterInfo = topsterService.getTopsterInfoByUserId(userId);
        return ResponseEntity.ok().body(topsterInfo);
    }

    @Operation(summary = "탑스터 삭제", description = "로그인한 유저는 topsterId로 탑스터를 삭제할 수 있다.")
    @DeleteMapping("/user/profile/topster")
    public ResponseEntity<?> removeTopster(@LoginUser UserEntity loggedInUser) {
        topsterService.removeTopsterByUserId(loggedInUser.getId());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "탑스터에 속한 앨범들 중 일부를 삭제", description = "로그인한 유저는 탑스터에 속한 앨범들 중 일부를 삭제할 수 있다.")
    @DeleteMapping("/user/profile/topster/album")
    public ResponseEntity<?> deleteAlbumsFromTopster(@LoginUser UserEntity loggedInUser, @RequestBody TopsterAlbumDeleteReqDto dto) {
        List<String> remainAlbumIds = topsterService.deleteAlbumsFromTopster(loggedInUser.getId(), dto.getAlbumIds());

        return ResponseEntity.ok().body(
                TopsterAlbumDeleteResDto.builder()
                        .remainAlbumIds(remainAlbumIds)
                        .build()
        );
    }

    @Operation(summary = "탑스터에 앨범 추가", description = "로그인한 유저는 탑스터에 앨범을 추가할 수 있다.")
    @PostMapping("/user/profile/topster/album")
    public ResponseEntity<?> appendAlbumsInTopster(@LoginUser UserEntity loggedInUser, @RequestBody TopsterAlbumAppendReqDto dto) {
        List<String> remainAlbumIds = topsterService.appendAlbumsInTopster(loggedInUser.getId(), dto.getAlbumIds());

        return ResponseEntity.ok().body(
                TopsterAlbumAppendResDto.builder()
                        .remainAlbumIds(remainAlbumIds)
                        .build()
        );
    }
}