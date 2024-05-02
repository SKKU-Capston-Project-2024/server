package site.mutopia.server.domain.profile.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import site.mutopia.server.aws.s3.FileManager;
import site.mutopia.server.domain.profile.dto.response.MyInfoResDto;
import site.mutopia.server.domain.profile.entity.ProfileEntity;
import site.mutopia.server.domain.profile.repository.ProfileRepository;
import site.mutopia.server.domain.user.entity.UserEntity;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final FileManager fileManager;

    @Transactional
    public MyInfoResDto getMyInfo(UserEntity userEntity) {
        ProfileEntity profile = profileRepository.findById(userEntity.getUserId()).orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다."));
        return new MyInfoResDto(profile.getProfileId(), userEntity.getUsername(), profile.getProfilePicUrl());
    }

    @Transactional
    public void editProfileImg(UserEntity userEntity, MultipartFile file) {
        try{
            String uploadURL = fileManager.uploadFile(file, userEntity.getUserId());
            profileRepository.findById(userEntity.getUserId()).ifPresent(profile -> {
                profile.setProfilePicUrl(uploadURL);
                profileRepository.save(profile);
            });
        }
        catch (Exception e){
            throw new IllegalArgumentException("Failed to upload file");
        }
    }
}
