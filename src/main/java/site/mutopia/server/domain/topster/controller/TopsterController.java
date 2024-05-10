package site.mutopia.server.domain.topster.controller;

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

import java.util.List;

@RestController
@RequestMapping("/topster")
@RequiredArgsConstructor
@Tag(name = "Topster", description = "Topster APIs")
public class TopsterController {

    private final TopsterService topsterService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CreatedResponse
    public ResponseEntity<TopsterSaveResDto> saveTopster(@LoginUser UserEntity loggedInUser, @RequestBody TopsterSaveReqDto saveDto) {
        TopsterEntity savedTopster = topsterService.saveTopster(loggedInUser.getId(), saveDto);
        return ResponseEntity.ok().body(TopsterSaveResDto.builder().topsterId(savedTopster.getId()).build());
    }

    @GetMapping("/{topsterId}")
    public ResponseEntity<TopsterInfoDto> getTopsterById(@PathVariable("topsterId") Long topsterId) {
        TopsterInfoDto topsterInfo = topsterService.getTopsterInfoById(topsterId);
        return ResponseEntity.ok().body(topsterInfo);
    }


    @DeleteMapping("/{topsterId}/album")
    public ResponseEntity<?> deleteAlbumsFromTopster(@LoginUser UserEntity loggedInUser, @PathVariable("topsterId") Long topsterId, @RequestBody TopsterAlbumDeleteReqDto dto) {
        boolean userOwnsTopster = topsterService.userOwnsTopster(loggedInUser.getId(), topsterId);

        if (!userOwnsTopster) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Error: You do not have permission to modify this Topster.");
        }

        List<String> remainAlbumIds = topsterService.deleteAlbumsFromTopster(topsterId, dto.getAlbumIds());

        return ResponseEntity.ok().body(
                TopsterAlbumDeleteResDto.builder()
                        .remainAlbumIds(remainAlbumIds)
                        .build()
        );
    }

    @PostMapping("/{topsterId}/album")
    public ResponseEntity<?> appendAlbumsInTopster(@LoginUser UserEntity loggedInUser, @PathVariable("topsterId") Long topsterId, @RequestBody TopsterAlbumAppendReqDto dto) {
        boolean userOwnsTopster = topsterService.userOwnsTopster(loggedInUser.getId(), topsterId);

        if (!userOwnsTopster) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Error: You do not have permission to modify this Topster.");
        }

        List<String> remainAlbumIds = topsterService.appendAlbumsInTopster(topsterId, dto.getAlbumIds());

        return ResponseEntity.ok().body(
                TopsterAlbumAppendResDto.builder()
                        .remainAlbumIds(remainAlbumIds)
                        .build()
        );
    }
}