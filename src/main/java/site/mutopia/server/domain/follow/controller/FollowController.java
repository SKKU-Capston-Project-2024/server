package site.mutopia.server.domain.follow.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import site.mutopia.server.domain.auth.annotation.LoginUser;
import site.mutopia.server.domain.follow.dto.FollowerInfoDto;
import site.mutopia.server.domain.follow.service.FollowService;
import site.mutopia.server.domain.user.entity.UserEntity;
import site.mutopia.server.swagger.response.CreatedResponse;
import site.mutopia.server.swagger.response.NotFoundResponse;
import site.mutopia.server.swagger.response.OkResponse;

import java.util.List;

@Tag(name = "Follow", description = "팔로우 관련 API")
@RequiredArgsConstructor
@RestController
public class FollowController {

    private final FollowService followService;

    @Operation(summary = "팔로워 목록 조회", description = "사용자의 팔로워 목록을 조회합니다.")
    @GetMapping("/user/{userId}/followers")
    public ResponseEntity<List<FollowerInfoDto>> getFollowers(@PathVariable("userId") String userId) {
        return ResponseEntity.ok().body(followService.getFollowers(userId));
    }

    @Operation(summary = "팔로잉 목록 조회", description = "사용자의 팔로잉 목록을 조회합니다.")
    @GetMapping("/user/{userId}/followings")
    public ResponseEntity<List<FollowerInfoDto>> getFollowings(@PathVariable("userId") String userId) {
        return ResponseEntity.ok().body(followService.getFollowings(userId));
    }

    @Operation(summary = "팔로우 하기", description = "다른 사용자를 팔로우합니다.")
    @CreatedResponse
    @NotFoundResponse
    @PostMapping("/user/following")
    public ResponseEntity<?> follow(
            @LoginUser UserEntity user,
            @RequestParam("userId") String userId){
        followService.follow(user, userId);
        return ResponseEntity.status(201).build();
    }

    @Operation(summary = "언팔로우 하기", description = "다른 사용자를 언팔로우합니다.")
    @OkResponse
    @NotFoundResponse
    @DeleteMapping("/user/following")
    public ResponseEntity<?> unfollow(
            @LoginUser UserEntity user,
            @RequestParam("userId") String userId){
        followService.unfollow(user, userId);
        return ResponseEntity.ok().build();
    }

}
