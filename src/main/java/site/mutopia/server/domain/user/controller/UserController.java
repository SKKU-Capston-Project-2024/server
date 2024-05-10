package site.mutopia.server.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.mutopia.server.domain.auth.annotation.LoginUser;
import site.mutopia.server.domain.topster.dto.TopsterInfoDto;
import site.mutopia.server.domain.topster.service.TopsterService;
import site.mutopia.server.domain.user.dto.UserAggregationInfoResDto;
import site.mutopia.server.domain.user.entity.UserEntity;
import site.mutopia.server.domain.user.service.UserService;
import site.mutopia.server.swagger.response.NotFoundResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final TopsterService topsterService;

    @GetMapping("/info")
    public ResponseEntity<UserEntity> getUserInfo(@LoginUser UserEntity userEntity) {
        return ResponseEntity.ok().body(userEntity);
    }

    @GetMapping("/{userId}/profile/aggregation")
    public ResponseEntity<UserAggregationInfoResDto> getUserAggregationInfo(@PathVariable("userId") String userId) {
        UserAggregationInfoResDto result = userService.aggregateUserInfo(userId);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/{userId}/topster")
    @NotFoundResponse
    public ResponseEntity<TopsterInfoDto> getUserTopster(@PathVariable("userId") String userId) {
        TopsterInfoDto topster = topsterService.getTopsterInfoByUserId(userId);
        return ResponseEntity.ok().body(topster);
    }
}
