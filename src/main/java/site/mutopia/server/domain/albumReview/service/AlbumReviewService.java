package site.mutopia.server.domain.albumReview.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import site.mutopia.server.domain.album.entity.AlbumEntity;
import site.mutopia.server.domain.album.exception.AlbumNotFoundException;
import site.mutopia.server.domain.album.repository.AlbumRepository;
import site.mutopia.server.domain.albumReview.dto.AlbumReviewCheckResDto;
import site.mutopia.server.domain.albumReview.dto.AlbumReviewInfoDto;
import site.mutopia.server.domain.albumReview.dto.AlbumReviewSaveReqDto;
import site.mutopia.server.domain.albumReview.entity.AlbumReviewEntity;
import site.mutopia.server.domain.albumReview.exception.AlbumReviewAlreadyExistException;
import site.mutopia.server.domain.albumReview.exception.AlbumReviewNotFoundException;
import site.mutopia.server.domain.albumReview.repository.AlbumReviewRepository;
import site.mutopia.server.domain.albumReviewLike.repository.AlbumReviewLikeRepository;
import site.mutopia.server.domain.user.entity.UserEntity;
import site.mutopia.server.domain.user.exception.UserNotFoundException;
import site.mutopia.server.domain.user.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AlbumReviewService {

    private static final Logger log = LoggerFactory.getLogger(AlbumReviewService.class);
    private final AlbumReviewRepository albumReviewRepository;
    private final AlbumReviewLikeRepository reviewLikeRepository;
    private final UserRepository userRepository;
    private final AlbumRepository albumRepository;

    public AlbumReviewEntity saveAlbumReview(String writerId, AlbumReviewSaveReqDto reviewSaveDto) {
        albumReviewRepository.findByWriterIdAndAlbumId(writerId, reviewSaveDto.getAlbumId()).ifPresent(review -> {
            throw new AlbumReviewAlreadyExistException("User already reviewed album: " + reviewSaveDto.getAlbumId());
        });

        UserEntity writer = userRepository.findById(writerId).orElseThrow(() -> new UserNotFoundException("User not found with ID: " + writerId));
        AlbumEntity album = albumRepository.findAlbumById(reviewSaveDto.getAlbumId()).orElseThrow(() -> new AlbumNotFoundException("Album not found. albumId: " + reviewSaveDto.getAlbumId() + " does not exist."));
        AlbumReviewEntity albumReview = reviewSaveDto.toEntity(writer, album);

        return albumReviewRepository.save(albumReview);
    }

    public AlbumReviewInfoDto getAlbumReviewInfoById(UserEntity user,Long albumReviewId) {

        return albumReviewRepository.findAlbumReviewInfoDto(albumReviewId, user == null ? null : user.getId())
                .orElseThrow(() -> new AlbumReviewNotFoundException("Album Review not found. albumReviewId: " + albumReviewId + " does not exist."));

    }

    public AlbumReviewEntity getMyAlbumReview(String writerId, String albumId) {
        return albumReviewRepository.findByAlbumIdAndUserId(writerId, albumId)
                .orElseThrow(() -> new AlbumReviewNotFoundException("Album Review not found. writerId: " + writerId + " albumId: " + albumId + " does not exist."));
    }

    public List<AlbumReviewInfoDto> findAlbumReviewInfoDtoListByUserId(UserEntity loggingUser, String userId, Integer offset) {
        return albumReviewRepository.findAllByUserIdOrderByCreatedAt(userId, offset, loggingUser == null ? null : loggingUser.getId());

    }

    public AlbumReviewCheckResDto checkReviewExistence(String userId, String albumId) {
        Optional<AlbumReviewEntity> albumReview = albumReviewRepository.findByWriterIdAndAlbumId(userId, albumId);

        if (albumReview.isPresent()) {
            return AlbumReviewCheckResDto.builder()
                    .userHasReviewed(true)
                    .albumReviewId(albumReview.get().getId())
                    .build();
        }

        return AlbumReviewCheckResDto.builder().userHasReviewed(false).albumReviewId(null).build();
    }


    public List<AlbumReviewInfoDto> getRecentAlbumReviews(UserEntity userEntity, String albumId, int offset) {
        return albumReviewRepository.findAllByAlbumIdOrderByCreatedAt(albumId, offset, userEntity == null ? null : userEntity.getId());
    }

    public List<AlbumReviewInfoDto> getRecentAlbumReview(UserEntity userEntity, int offset) {
        return albumReviewRepository.findAllOrderByCreatedAtDesc(offset, userEntity == null ? null : userEntity.getId());
    }

    private AlbumReviewInfoDto addIsLikedToReview(UserEntity user, AlbumReviewInfoDto review) {
        if (user != null) {
            review.getReview().setIsLiked(reviewLikeRepository.existsByReview_IdAndUser_Id(review.getReview().getId(), user.getId()));
        }
        return review;
    }

    public List<AlbumReviewInfoDto> getPopularAlbumReviewsByAlbumId(UserEntity userEntity, String albumId, int offset) {
        return albumReviewRepository.findAllByAlbumIdOrderByLike(userEntity == null ? null : userEntity.getId(), albumId, offset);
    }

    public List<AlbumReviewInfoDto> getPopularAlbumReviews(UserEntity userEntity, int offset) {
        return albumReviewRepository.findAllOrderByLikeDesc(userEntity == null ? null : userEntity.getId(), offset);
    }

    public List<AlbumReviewInfoDto> findByUserIdOrderByLike(String userId, Integer offset, String loginUserId) {
        return albumReviewRepository.findAllByUserIdOrderByLike(userId, offset, loginUserId);
    }

}
