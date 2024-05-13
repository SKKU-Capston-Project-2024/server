package site.mutopia.server.domain.albumLike.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.mutopia.server.domain.album.entity.AlbumEntity;
import site.mutopia.server.domain.album.exception.AlbumNotFoundException;
import site.mutopia.server.domain.album.repository.AlbumRepository;
import site.mutopia.server.domain.albumLike.entity.AlbumLikeEntity;
import site.mutopia.server.domain.albumLike.repository.AlbumLikeRepository;
import site.mutopia.server.domain.user.entity.UserEntity;
import site.mutopia.server.domain.user.exception.UserNotFoundException;
import site.mutopia.server.domain.user.repository.UserRepository;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AlbumLikeService {

    private final AlbumLikeRepository albumLikeRepository;
    private final AlbumRepository albumRepository;
    private final UserRepository userRepository;

    public void toggleAlbumLike(String albumId, String userId) {
        if (albumLikeRepository.existsByAlbumIdAndUserId(albumId, userId)) {
            albumLikeRepository.deleteByAlbumIdAndUserId(albumId, userId);
            return;
        }

        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found. userId: " + userId + " does not exist."));
        AlbumEntity album = albumRepository.findAlbumById(albumId).orElseThrow(() -> new AlbumNotFoundException("Album not found. albumId: " + albumId + " does not exist."));

        albumLikeRepository.save(AlbumLikeEntity.builder()
                .album(album)
                .user(user)
                .build());
    }

    @Transactional(readOnly = true)
    public boolean isAlbumLikeExists(String albumId, String userId) {
        return albumLikeRepository.existsByAlbumIdAndUserId(albumId, userId);
    }

    @Transactional(readOnly = true)
    public boolean checkAlbumLikeStatus(String albumId, UserEntity user) {
        Optional<AlbumEntity> album = albumRepository.findAlbumById(albumId);
        return album.map(a -> albumLikeRepository.findByAlbumAndUser(a, user).isPresent()).orElse(false);
    }
}
