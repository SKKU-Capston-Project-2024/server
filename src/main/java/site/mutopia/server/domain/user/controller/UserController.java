package site.mutopia.server.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.mutopia.server.domain.albumReview.dto.AlbumReviewInfoDto;
import site.mutopia.server.domain.albumReview.service.AlbumReviewService;
import site.mutopia.server.domain.auth.annotation.LoginUser;
import site.mutopia.server.domain.topster.dto.TopsterInfoDto;
import site.mutopia.server.domain.topster.service.TopsterService;
import site.mutopia.server.domain.user.dto.UserAggregationInfoResDto;
import site.mutopia.server.domain.user.entity.UserEntity;
import site.mutopia.server.domain.user.service.UserService;
import site.mutopia.server.swagger.response.NotFoundResponse;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final TopsterService topsterService;
    private final AlbumReviewService albumReviewService;

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

    @GetMapping("/{userId}/album/review")
    public ResponseEntity<List<AlbumReviewInfoDto>> getUserAlbumReview(@PathVariable("userId") String userId,
                                               @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit) {
        List<AlbumReviewInfoDto> reviews = albumReviewService.findAlbumReviewInfoDtoListByUserId(userId, limit);
        return ResponseEntity.ok(reviews);
    }
}
