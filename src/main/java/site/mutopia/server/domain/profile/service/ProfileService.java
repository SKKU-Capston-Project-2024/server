package site.mutopia.server.domain.profile.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import site.mutopia.server.aws.s3.FileManager;
import site.mutopia.server.domain.profile.dto.response.MyInfoResDto;
import site.mutopia.server.domain.profile.entity.ProfileEntity;
import site.mutopia.server.domain.profile.exception.ProfileNotFoundException;
import site.mutopia.server.domain.profile.repository.ProfileRepository;
import site.mutopia.server.domain.user.entity.UserEntity;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final FileManager fileManager;

    public MyInfoResDto getMyInfo(UserEntity userEntity) {
        Optional<ProfileEntity> profile = profileRepository.findByUserId(userEntity.getId());

        if (profile == null) {
            ProfileEntity savedProfile = saveNewUserProfile(userEntity);
            return MyInfoResDto.builder().id(userEntity.getId()).name(userEntity.getUsername()).profileUrl(savedProfile.getProfilePicUrl()).bio(null).isFirstLogin(true).build();
        }

        return MyInfoResDto.builder().id(userEntity.getId()).name(userEntity.getUsername()).profileUrl(profile.get().getProfilePicUrl()).bio(profile.get().getBio()).isFirstLogin(false).build();
    }

    private ProfileEntity saveNewUserProfile(UserEntity userEntity) {
        // TODO: extract default profile img url to env
        return profileRepository.save(ProfileEntity.newUserProfile(userEntity, "https://mutopia.s3.ap-northeast-2.amazonaws.com/default/defaultProfile.svg"));
    }

    public void editProfile(UserEntity userEntity, String username, String bio, MultipartFile file) {
        try {
            if (username != null && !username.isEmpty()) {
                userEntity.modifyUsername(username);
            }

            ProfileEntity profile = profileRepository.findByUserId(userEntity.getId()).orElseThrow(() -> new ProfileNotFoundException("Profile not found that matches to userId: " + userEntity.getId()));

            if (bio != null && !bio.isEmpty()) {
                profile.modifyBio(bio);
            }

            if (file != null) {
                profile.modifyProfilePicUrl(fileManager.uploadFile(file, userEntity.getId()));
            }

        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to upload file");
        }
    }
}
