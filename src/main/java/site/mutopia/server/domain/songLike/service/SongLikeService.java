package site.mutopia.server.domain.songLike.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.mutopia.server.domain.song.SongNotFoundException;
import site.mutopia.server.domain.song.dto.SongInfoDto;
import site.mutopia.server.domain.song.entity.SongEntity;
import site.mutopia.server.domain.song.repository.SongRepository;
import site.mutopia.server.domain.songLike.dto.LikeSongDto;
import site.mutopia.server.domain.songLike.entity.SongLikeEntity;
import site.mutopia.server.domain.songLike.repository.SongLikeRepository;
import site.mutopia.server.domain.user.entity.UserEntity;
import site.mutopia.server.domain.user.exception.UserNotFoundException;
import site.mutopia.server.domain.user.repository.UserRepository;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SongLikeService {

    private final SongLikeRepository songLikeRepository;
    private final SongRepository songRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public Long countLikesBySongId(String songId) {
        return songLikeRepository.countLikesBySongId(songId);
    }

    public void toggleSongLike(String songId, String userId) {
        if (songLikeRepository.existsBySongIdAndUserId(songId, userId)) {
            songLikeRepository.deleteBySongIdAndUserId(songId, userId);
            return;
        }

        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found. userId: " + userId + " does not exist."));
        SongEntity song = songRepository.findById(songId).orElseThrow(() -> new SongNotFoundException(songId));

        songLikeRepository.save(SongLikeEntity.builder()
                .song(song)
                .user(user)
                .build());
    }

    @Transactional(readOnly = true)
    public boolean isSongLikeExists(String songId, String userId) {
        return songLikeRepository.existsBySongIdAndUserId(songId, userId);
    }

    @Transactional(readOnly = true)
    public List<LikeSongDto> getLikedSongsByUser(String userId, Integer page){
        List<SongLikeEntity> songs = songLikeRepository.findLikedSongsByUserId(userId, Pageable.ofSize(10).withPage(page));
        return songs.stream().map(LikeSongDto::of).toList();
    }

}
