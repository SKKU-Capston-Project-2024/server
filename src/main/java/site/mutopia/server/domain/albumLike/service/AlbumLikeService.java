package site.mutopia.server.domain.albumLike.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.mutopia.server.domain.album.entity.AlbumEntity;
import site.mutopia.server.domain.album.repository.AlbumRepository;
import site.mutopia.server.domain.albumLike.entity.AlbumLikeEntity;
import site.mutopia.server.domain.albumLike.repository.AlbumLikeRepository;
import site.mutopia.server.domain.user.entity.UserEntity;
import site.mutopia.server.domain.user.exception.UserNotFoundException;
import site.mutopia.server.domain.user.repository.UserRepository;

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
        AlbumEntity album = albumRepository.findAlbumById(albumId);

        albumLikeRepository.save(AlbumLikeEntity.builder()
                .albumId(album)
                .userId(user)
                .build());
    }
}
