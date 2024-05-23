package site.mutopia.server.domain.playlistLike.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.mutopia.server.domain.playlist.dto.PlaylistInfoDto;
import site.mutopia.server.domain.playlist.entity.PlaylistEntity;
import site.mutopia.server.domain.playlist.exception.PlaylistNotFoundException;
import site.mutopia.server.domain.playlist.repository.PlaylistRepository;
import site.mutopia.server.domain.playlistLike.entity.PlaylistLikeEntity;
import site.mutopia.server.domain.playlistLike.repository.PlaylistLikeRepository;
import site.mutopia.server.domain.user.entity.UserEntity;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PlaylistLikeService {

    private final PlaylistRepository playlistRepository;
    private final PlaylistLikeRepository playlistLikeRepository;

    public void toggleLike(UserEntity loggedInUser, Long playlistId) {
        PlaylistEntity playlist = playlistRepository.findById(playlistId).orElseThrow(() -> new PlaylistNotFoundException("Playlist not found. playlistId: " + playlistId + " does not exist."));

        boolean isLiked = playlistLikeRepository.existsByUserIdAndPlaylistId(loggedInUser.getId(), playlist.getId());

        if (isLiked) {
            playlistLikeRepository.deleteByUserIdAndPlaylistId(loggedInUser.getId(), playlist.getId());
            return;
        }

        PlaylistLikeEntity likeEntity = PlaylistLikeEntity.builder()
                .user(loggedInUser)
                .playlist(playlist)
                .build();
        playlistLikeRepository.save(likeEntity);
    }

    @Transactional(readOnly = true)
    public boolean isPlaylistLikeExists(Long playlistId, String userId) {
        return playlistLikeRepository.existsByUserIdAndPlaylistId(userId, playlistId);
    }

    @Transactional(readOnly = true)
    public Long countLikesByPlaylistId(Long playlistId) {
        return playlistLikeRepository.countLikesByPlaylistId(playlistId);
    }


}
