package site.mutopia.server.domain.profile.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import site.mutopia.server.domain.auth.annotation.LoginUser;
import site.mutopia.server.domain.profile.dto.PinAlbumReviewToProfileReq;
import site.mutopia.server.domain.profile.dto.response.MyInfoResDto;
import site.mutopia.server.domain.profile.service.ProfileService;
import site.mutopia.server.domain.profile.dto.response.ProfileAggregationInfoResDto;
import site.mutopia.server.domain.user.entity.UserEntity;
import site.mutopia.server.swagger.response.NotFoundResponse;
import site.mutopia.server.swagger.response.OkResponse;

@RestController
@Tag(name = "Profile", description = "Profile APIs")
@RequiredArgsConstructor
@RequestMapping
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/user/profile/me")
    public ResponseEntity<MyInfoResDto> getMyInfo(
            @LoginUser UserEntity userEntity) {
        return ResponseEntity.ok().body(profileService.getMyInfo(userEntity));
    }

    @PatchMapping(value="/user/profile/me", consumes = {"multipart/form-data"})
    public ResponseEntity<Void> editProfile(
            @LoginUser UserEntity userEntity,
            @RequestParam(value = "userName",required = false) String username,
            @RequestParam(value = "file",required = false) MultipartFile file,
            @RequestParam(value = "bio",required = false) String bio) {
        profileService.editProfile(userEntity, username,bio, file);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "유저 정보 가져오기", description = "유저의 닉네임, 프로필 사진, 리뷰, 좋아요, 팔로워, 팔로잉 수를 가져옵니다.")
    @NotFoundResponse
    @OkResponse
    @GetMapping("/user/{userId}/profile/aggregation")
    public ResponseEntity<ProfileAggregationInfoResDto> getProfileAggregationInfo(@PathVariable("userId") String userId) {
        ProfileAggregationInfoResDto result = profileService.aggregateProfileInfo(userId);
        return ResponseEntity.ok().body(result);
    }

    @Operation(summary = "유저 프로필에 자신의 리뷰 고정(pin)시키기", description = "로그인한 유저는 자신의 프로필에 자신의 리뷰를 고정시킬 수 있습니다.")
    @PostMapping("/user/profile/album/review/pin")
    public ResponseEntity<Void> pinAlbumReviewToProfile(@LoginUser UserEntity user, @RequestBody PinAlbumReviewToProfileReq req) {
        profileService.pinAlbumReview(user, req.getAlbumReviewId());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "유저 프로필에 자신의 리뷰 고정(pin) 해제하기", description = "로그인한 유저는 자신의 프로필에 자신의 리뷰를 고정 해제 할 수 있습니다. 유저가 고정한 리뷰가 있다면 고정해제 시킵니다.")
    @PostMapping("/user/profile/album/review/unpin")
    public ResponseEntity<Void> unpinAlbumReviewToProfile(@LoginUser UserEntity user) {
        profileService.unPinAlbumReview(user);
        return ResponseEntity.ok().build();
    }

}
