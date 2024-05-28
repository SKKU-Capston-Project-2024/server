package site.mutopia.server.domain.profile.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import site.mutopia.server.aws.s3.FileManager;
import site.mutopia.server.domain.albumReview.entity.AlbumReviewEntity;
import site.mutopia.server.domain.albumReview.exception.AlbumReviewNotFoundException;
import site.mutopia.server.domain.albumReview.repository.AlbumReviewRepository;
import site.mutopia.server.domain.follow.repository.FollowRepository;
import site.mutopia.server.domain.profile.dto.response.MyInfoResDto;
import site.mutopia.server.domain.profile.dto.response.ProfileAggregationInfoResDto;
import site.mutopia.server.domain.profile.entity.ProfileEntity;
import site.mutopia.server.domain.profile.exception.ProfileNotFoundException;
import site.mutopia.server.domain.profile.repository.ProfileRepository;
import site.mutopia.server.domain.songComment.repository.SongCommentRepository;
import site.mutopia.server.domain.user.entity.UserEntity;
import site.mutopia.server.domain.user.exception.UserNotFoundException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final FileManager fileManager;
    private final AlbumReviewRepository albumReviewRepository;
    private final FollowRepository followRepository;
    private final SongCommentRepository songCommentRepository;

    public MyInfoResDto getMyInfo(UserEntity userEntity) {
        Optional<ProfileEntity> profile = profileRepository.findByUserId(userEntity.getId());

        if (profile.isEmpty()) {
            ProfileEntity savedProfile = saveNewUserProfile(userEntity);
            return MyInfoResDto.builder().id(userEntity.getId()).name(userEntity.getUsername()).profileUrl(savedProfile.getProfilePicUrl()).bio(null).isFirstLogin(true).build();
        }

        return MyInfoResDto.builder().id(userEntity.getId()).name(userEntity.getUsername()).profileUrl(profile.get().getProfilePicUrl()).bio(profile.get().getBiography()).isFirstLogin(false).build();
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

    @Transactional(readOnly = true)
    public ProfileAggregationInfoResDto aggregateProfileInfo(String userId) {
        ProfileEntity profileEntity = profileRepository.findByUserId(userId).orElseThrow(() -> new UserNotFoundException("해당 유저의 프로필이 존재하지 않습니다."));
        Long totalReviewCount = albumReviewRepository.countByWriterId(userId);
        Long totalRatingCount = totalReviewCount + songCommentRepository.countByWriterId(userId);
        Long followerCount = followRepository.countByFollowingId(userId);
        Long followingCount = followRepository.countByUserId(userId);

        return ProfileAggregationInfoResDto.builder()
                .totalReviewCount(totalReviewCount)
                .totalRatingCount(totalRatingCount)
                .followerCount(followerCount)
                .followingCount(followingCount)
                .userId(userId)
                .biography(profileEntity.getBiography())
                .username(profileEntity.getUser().getUsername())
                .profileImageUrl(profileEntity.getProfilePicUrl())
                .build();
    }

    public void pinAlbumReview(UserEntity user, Long albumReviewId) {
        AlbumReviewEntity review = albumReviewRepository.findById(albumReviewId)
                .orElseThrow(() -> new AlbumReviewNotFoundException("Album Review not found. albumReviewId: " + albumReviewId + " does not exist."));

        if (!review.getWriter().getId().equals(user.getId())) {
            // TODO: ControllerAdvice 처리
            throw new IllegalStateException("You can only pin your own reviews.");

            // throw new UnauthorizedAccessException("You can only pin your own reviews.");
        }

        albumReviewRepository.updatePinnedToFalseByUserId(user.getId());
        review.pin();
    }

    public void unPinAlbumReview(UserEntity user) {
        albumReviewRepository.updatePinnedToFalseByUserId(user.getId());
    }
}
