package site.mutopia.server.domain.follow.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.mutopia.server.domain.follow.dto.FollowerInfoDto;
import site.mutopia.server.domain.follow.service.FollowService;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class FollowController {

    private final FollowService followService;

    @GetMapping("/user/{userId}/followers")
    public List<FollowerInfoDto> getFollowers(@PathVariable("userId") String userId) {
        return followService.getFollowers(userId);
    }



}
