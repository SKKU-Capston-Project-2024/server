package site.mutopia.server.domain.profile.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import site.mutopia.server.aws.s3.FileManager;
import site.mutopia.server.domain.profile.dto.response.MyInfoResDto;
import site.mutopia.server.domain.profile.entity.ProfileEntity;
import site.mutopia.server.domain.profile.repository.ProfileRepository;
import site.mutopia.server.domain.user.entity.UserEntity;
import site.mutopia.server.domain.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final FileManager fileManager;
    private final UserRepository userRepository;

    @Transactional
    public MyInfoResDto getMyInfo(UserEntity userEntity) {
        ProfileEntity profile = profileRepository.findByUserUserId(userEntity.getUserId()).orElse(null);
        if(profile==null){
            profile = saveNewUserProfile(userEntity);
            return new MyInfoResDto(userEntity.getUserId(), userEntity.getUsername(), profile.getProfilePicUrl(),null,true);
        }
        return new MyInfoResDto(userEntity.getUserId(), userEntity.getUsername(), profile.getProfilePicUrl(),profile.getBio(),false);
    }

    private ProfileEntity saveNewUserProfile(UserEntity userEntity){
        ProfileEntity profile = new ProfileEntity();
        profile.setProfilePicUrl("https://mutopia.s3.ap-northeast-2.amazonaws.com/default/defaultProfile.svg");
        profile.setUser(userEntity);
        profileRepository.save(profile);
        return profile;
    }

    @Transactional
    public void editProfile(UserEntity userEntity, String username, String bio, MultipartFile file) {
        try{
            if(username!=null && !username.isEmpty()){
                userEntity.setUsername(username);
                userRepository.save(userEntity);
            }
            ProfileEntity profile = profileRepository.findByUserUserId(userEntity.getUserId()).orElseThrow(() -> new IllegalArgumentException("Profile not found"));
            if(bio!=null && !bio.isEmpty()){
                profile.setBio(bio);
            }
            if(file!=null){
                String uploadURL = fileManager.uploadFile(file, userEntity.getUserId());
                profile.setProfilePicUrl(uploadURL);
            }
            profileRepository.save(profile);
        }
        catch (Exception e){
            throw new IllegalArgumentException("Failed to upload file");
        }
    }
}
