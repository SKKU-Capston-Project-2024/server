package site.mutopia.server.domain.topster.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.mutopia.server.domain.auth.annotation.LoginUser;
import site.mutopia.server.domain.topster.dto.TopsterInfoDto;
import site.mutopia.server.domain.topster.dto.TopsterSaveReqDto;
import site.mutopia.server.domain.topster.dto.TopsterSaveResDto;
import site.mutopia.server.domain.topster.entity.TopsterEntity;
import site.mutopia.server.domain.topster.service.TopsterService;
import site.mutopia.server.domain.user.entity.UserEntity;
import site.mutopia.server.swagger.response.CreatedResponse;

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
        TopsterInfoDto topsterInfo = topsterService.getTopsterInfo(topsterId);
        return ResponseEntity.ok().body(topsterInfo);
    }
}