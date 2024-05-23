package site.mutopia.server.domain.youtubePlaylist.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.mutopia.server.domain.user.entity.UserEntity;
import site.mutopia.server.domain.user.exception.UserNotFoundException;
import site.mutopia.server.domain.user.repository.UserRepository;
import site.mutopia.server.domain.youtubePlaylist.entity.YoutubePlaylistEntity;
import site.mutopia.server.domain.youtubePlaylist.repository.YoutubePlaylistRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class YoutubePlaylistCRUDService {
    private final YoutubePlaylistRepository youtubePlaylistRepository;
    private final UserRepository userRepository;

    public void savePlaylist(String ytPlaylistId, String userId) {
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found. userId: " + userId + " does not exist."));
        YoutubePlaylistEntity youtubePlaylist = YoutubePlaylistEntity.builder()
                .id(ytPlaylistId)
                .creator(user)
                .build();

        youtubePlaylistRepository.save(youtubePlaylist);
    }

    public List<YoutubePlaylistEntity> findAllPlaylists(String userId) {
        return youtubePlaylistRepository.findAllByCreatorId(userId);
    }
}
