package site.mutopia.server.domain.albumReview.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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
import site.mutopia.server.domain.albumReviewLike.entity.AlbumReviewLikeId;
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

    private final AlbumReviewRepository albumReviewRepository;
    private final AlbumReviewLikeRepository reviewLikeRepository;
    private final UserRepository userRepository;
    private final AlbumRepository albumRepository;

    public AlbumReviewEntity saveAlbumReview(String writerId, AlbumReviewSaveReqDto reviewSaveDto) {
        albumReviewRepository.findByWriterId(writerId).ifPresent(review -> {
            throw new AlbumReviewAlreadyExistException("User already reviewed album: " + reviewSaveDto.getAlbumId());
        });

        UserEntity writer = userRepository.findById(writerId).orElseThrow(() -> new UserNotFoundException("User not found with ID: " + writerId));
        AlbumEntity album = albumRepository.findAlbumById(reviewSaveDto.getAlbumId()).orElseThrow(() -> new AlbumNotFoundException("Album not found. albumId: " + reviewSaveDto.getAlbumId() + " does not exist."));
        AlbumReviewEntity albumReview = reviewSaveDto.toEntity(writer, album);

        return albumReviewRepository.save(albumReview);
    }

    public AlbumReviewInfoDto getAlbumReviewInfoById(UserEntity user,Long albumReviewId) {

        AlbumReviewInfoDto albumReviewInfoDto = albumReviewRepository.findAlbumReviewInfoDto(albumReviewId).orElseThrow(() -> new AlbumReviewNotFoundException("Album Review not found. albumReviewId: " + albumReviewId + " does not exist."));
        if(user != null) {
            albumReviewInfoDto.getReview().setIsLiked(reviewLikeRepository.findById(new AlbumReviewLikeId(user.getId(),albumReviewId)).isPresent());
        }
        return albumReviewInfoDto;
    }

    public AlbumReviewEntity getMyAlbumReview(String writerId, String albumId) {
        return albumReviewRepository.findByAlbumIdAndUserId(writerId, albumId)
                .orElseThrow(() -> new AlbumReviewNotFoundException("Album Review not found. writerId: " + writerId + " albumId: " + albumId + " does not exist."));
    }

    public List<AlbumReviewInfoDto> findAlbumReviewInfoDtoListByUserId(UserEntity loggingUser, String userId, Integer limit) {
        List<AlbumReviewInfoDto> review = albumReviewRepository.findAlbumReviewInfoDtoListByUserId(userId, limit);
        if (loggingUser != null) {
            review.forEach(r -> {
                r.getReview().setIsLiked(reviewLikeRepository.findById(new AlbumReviewLikeId(loggingUser.getId(), r.getReview().getId())).isPresent());
            });
        }
        return review;

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


    public List<AlbumReviewInfoDto> getRecentAlbumReviews(UserEntity userEntity, String albumId,int offset) {

        List<AlbumReviewEntity> recentReviewByAlbumId = albumReviewRepository.findRecentReviewByAlbumId(albumId, 10, offset);
        List<AlbumReviewInfoDto> list = recentReviewByAlbumId.stream()
                .map(AlbumReviewInfoDto::fromEntity).toList();
        if(userEntity!=null){
            list.forEach(r -> {
                r.getReview().setIsLiked(reviewLikeRepository.findById(new AlbumReviewLikeId(userEntity.getId(), r.getReview().getId())).isPresent());
            });
        }
        return list;
    }

}
