package site.mutopia.server.domain.albumReviewLike.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.mutopia.server.domain.albumReview.entity.AlbumReviewEntity;
import site.mutopia.server.domain.albumReview.exception.AlbumReviewNotFoundException;
import site.mutopia.server.domain.albumReview.repository.AlbumReviewRepository;
import site.mutopia.server.domain.albumReviewLike.entity.AlbumReviewLikeEntity;
import site.mutopia.server.domain.albumReviewLike.repository.AlbumReviewLikeRepository;
import site.mutopia.server.domain.user.entity.UserEntity;
import site.mutopia.server.domain.user.exception.UserNotFoundException;
import site.mutopia.server.domain.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class AlbumReviewLikeService {

    private final AlbumReviewLikeRepository albumReviewLikeRepository;
    private final UserRepository userRepository;
    private final AlbumReviewRepository albumReviewRepository;

    @Transactional(readOnly = true)
    public Long countLikesByAlbumReviewId(Long albumReviewId) {
        return albumReviewLikeRepository.countLikesByAlbumReviewId(albumReviewId);
    }

    public void toggleAlbumReviewLike(Long albumReviewId, String userId) {
        if(albumReviewLikeRepository.existsByAlbumReviewIdAndUserId(albumReviewId, userId)) {
            albumReviewLikeRepository.deleteByAlbumReviewIdAndUserId(albumReviewId, userId);
            return;
        }

        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found. userId: " + userId + " does not exist."));
        AlbumReviewEntity albumReview = albumReviewRepository.findById(albumReviewId)
                .orElseThrow(() -> new AlbumReviewNotFoundException("Album Review not found. albumReviewId: " + albumReviewId + " does not exist."));

        albumReviewLikeRepository.save(AlbumReviewLikeEntity.builder()
                .user(user)
                .review(albumReview)
                .build());
    }

    @Transactional(readOnly = true)
    public boolean isAlbumReviewLikeExists(Long albumReviewId, String userId) {
        return albumReviewLikeRepository.existsByAlbumReviewIdAndUserId(albumReviewId, userId);
    }
}
