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
import site.mutopia.server.domain.albumReview.exception.AlbumReviewNotFoundException;
import site.mutopia.server.domain.albumReview.repository.AlbumReviewRepository;
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
    private final UserRepository userRepository;
    private final AlbumRepository albumRepository;

    public AlbumReviewEntity saveAlbumReview(String writerId, AlbumReviewSaveReqDto reviewSaveDto) {
        // TODO: 작성자가 해당 앨범에 대한 리뷰를 작성한 적이 있는지 체크하는 로직 추가하기

        UserEntity writer = userRepository.findById(writerId).orElseThrow(() -> new UserNotFoundException("User not found with ID: " + writerId));
        AlbumEntity album = albumRepository.findAlbumById(reviewSaveDto.getAlbumId()).orElseThrow(() -> new AlbumNotFoundException("Album not found. albumId: " + reviewSaveDto.getAlbumId() + " does not exist."));
        AlbumReviewEntity albumReview = reviewSaveDto.toEntity(writer, album);

        return albumReviewRepository.save(albumReview);
    }

    public AlbumReviewInfoDto getAlbumReviewInfoById(Long albumReviewId) {
        return albumReviewRepository.findAlbumReviewInfoDto(albumReviewId).orElseThrow(() -> new AlbumReviewNotFoundException("Album Review not found. albumReviewId: " + albumReviewId + " does not exist."));
    }

    public AlbumReviewEntity getMyAlbumReview(String writerId, String albumId) {
        return albumReviewRepository.findByAlbumIdAndUserId(writerId, albumId)
                .orElseThrow(() -> new AlbumReviewNotFoundException("Album Review not found. writerId: " + writerId + " albumId: " + albumId + " does not exist."));
    }
    public List<AlbumReviewInfoDto> findAlbumReviewInfoDtoListByUserId(String userId, Integer limit) {
        return albumReviewRepository.findAlbumReviewInfoDtoListByUserId(userId, limit);

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

}
