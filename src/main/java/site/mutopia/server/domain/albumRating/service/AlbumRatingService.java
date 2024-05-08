package site.mutopia.server.domain.albumRating.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.mutopia.server.domain.album.entity.AlbumEntity;
import site.mutopia.server.domain.album.exception.AlbumNotFoundException;
import site.mutopia.server.domain.album.repository.AlbumRepository;
import site.mutopia.server.domain.albumRating.entity.AlbumRatingEntity;
import site.mutopia.server.domain.albumRating.entity.AlbumRatingId;
import site.mutopia.server.domain.albumRating.exception.AlbumRatingNotFoundException;
import site.mutopia.server.domain.albumRating.repository.AlbumRatingRepository;
import site.mutopia.server.domain.user.entity.UserEntity;
import site.mutopia.server.domain.user.exception.UserNotFoundException;
import site.mutopia.server.domain.user.repository.UserRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class AlbumRatingService {

    private final AlbumRatingRepository albumRatingRepository;
    private final AlbumRepository albumRepository;
    private final UserRepository userRepository;

    public void saveAlbumRating(String albumId, String userId, Integer rating) {
        AlbumEntity album = albumRepository.findAlbumById(albumId).orElseThrow(() -> new AlbumNotFoundException("Album not found. albumId: " + albumId + " does not exist."));
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found. userId: " + userId + " does not exist."));

        AlbumRatingEntity albumRating = AlbumRatingEntity.builder()
                .album(album)
                .user(user)
                .rating(rating)
                .build();

        albumRatingRepository.save(albumRating);
    }

    public void modifyAlbumRating(String albumId, String userId, Integer rating) {
        AlbumEntity album = albumRepository.findAlbumById(albumId).orElseThrow(() -> new AlbumNotFoundException("Album not found. albumId: " + albumId + " does not exist."));
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found. userId: " + userId + " does not exist."));

        AlbumRatingEntity albumRating = albumRatingRepository.findById(AlbumRatingId.builder().album(album.getId()).user(user.getId()).build())
                .orElseThrow(() -> new AlbumRatingNotFoundException("Album rating not found. albumId: " + albumId + "userId: " + userId + " does not exist."));

        albumRating.modifyRating(rating);
    }

    public Integer getAlbumRating(String userId, String albumId) {
        AlbumRatingEntity albumRating = albumRatingRepository.findByUserIdAndAlbumId(userId, albumId)
                .orElseThrow(() -> new AlbumRatingNotFoundException("Album rating not found. albumId: " + albumId + "userId: " + userId + " does not exist."));

        return albumRating.getRating();
    }
}
