package site.mutopia.server.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.mutopia.server.domain.auth.annotation.LoginUser;
import site.mutopia.server.domain.profile.dto.response.UserAggregationInfoResDto;
import site.mutopia.server.domain.user.entity.UserEntity;
import site.mutopia.server.domain.user.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/info")
    public ResponseEntity<UserEntity> getUserInfo(@LoginUser UserEntity userEntity) {
        return ResponseEntity.ok().body(userEntity);
    }

    @GetMapping("/{userId}/profile/aggregation")
    public ResponseEntity<UserAggregationInfoResDto> getUserAggregationInfo(@PathVariable("userId") String userId) {
        UserAggregationInfoResDto result = userService.aggregateUserInfo(userId);
        return ResponseEntity.ok().body(result);
    }

}
