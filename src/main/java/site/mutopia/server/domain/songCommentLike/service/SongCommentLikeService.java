package site.mutopia.server.domain.songCommentLike.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.mutopia.server.domain.song.SongNotFoundException;
import site.mutopia.server.domain.song.entity.SongEntity;
import site.mutopia.server.domain.song.repository.SongRepository;
import site.mutopia.server.domain.songComment.entity.SongCommentEntity;
import site.mutopia.server.domain.songComment.entity.SongCommentId;
import site.mutopia.server.domain.songComment.exception.SongCommentNotFoundException;
import site.mutopia.server.domain.songComment.repository.SongCommentRepository;
import site.mutopia.server.domain.songCommentLike.dto.LikeSongCommentDto;
import site.mutopia.server.domain.songCommentLike.entity.SongCommentLikeEntity;
import site.mutopia.server.domain.songCommentLike.entity.SongCommentLikeId;
import site.mutopia.server.domain.songCommentLike.repository.SongCommentLikeRepository;
import site.mutopia.server.domain.user.entity.UserEntity;
import site.mutopia.server.domain.user.exception.UserNotFoundException;
import site.mutopia.server.domain.user.repository.UserRepository;

import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class SongCommentLikeService {

    private final SongCommentLikeRepository songCommentLikeRepository;
    private final SongCommentRepository songCommentRepository;
    private final SongRepository songRepository;
    private final UserRepository userRepository;

    public void toggleSongCommentLike(String songId, String writerId, UserEntity user) {

        SongCommentEntity songComment = songCommentRepository.findById(new SongCommentId(writerId, songId)).orElseThrow(
                () -> new SongNotFoundException(songId));


        SongCommentLikeId songCommentId = new SongCommentLikeId(new SongCommentId(writerId, songId), user.getId());

        if (songCommentLikeRepository.findById(songCommentId).isPresent()) {
            songCommentLikeRepository.deleteById(songCommentId);
            return;
        }

        SongCommentEntity songCommentEntity = songCommentRepository.findById(new SongCommentId(writerId, songId))
                .orElseThrow(() -> new SongCommentNotFoundException(writerId, songId));

        songCommentLikeRepository.save(SongCommentLikeEntity.builder()
                        .songComment(songCommentEntity)
                        .id(songCommentId)
                        .likeUser(user)
                        .build());
    }

    @Transactional(readOnly = true)
    public List<LikeSongCommentDto> getLikedSongsByUser(String userId, Integer page, UserEntity loggedInUser) {

        List<LikeSongCommentDto> likedSongsByUserId = songCommentLikeRepository.findLikedSongsByUserId(userId, Pageable.ofSize(10).withPage(page), loggedInUser != null ? loggedInUser.getId() : null);

        log.info("likedSongsByUserId: {}", likedSongsByUserId);
        return likedSongsByUserId;
    }

}
