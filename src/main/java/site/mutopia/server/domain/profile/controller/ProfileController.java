package site.mutopia.server.domain.profile.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import site.mutopia.server.domain.auth.annotation.LoginUser;
import site.mutopia.server.domain.profile.dto.response.MyInfoResDto;
import site.mutopia.server.domain.profile.service.ProfileService;
import site.mutopia.server.domain.user.entity.UserEntity;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/me")
    public ResponseEntity<MyInfoResDto> getMyInfo(@LoginUser UserEntity userEntity) {
        return ResponseEntity.ok().body(profileService.getMyInfo(userEntity));
    }

    @PostMapping("/edit/profileImg")
    public ResponseEntity<Void> editProfileImg(@LoginUser UserEntity userEntity, @RequestParam("file") MultipartFile file) {
        profileService.editProfileImg(userEntity, file);
        return ResponseEntity.ok().build();
    }

}
