package site.mutopia.server.domain.songComment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.mutopia.server.domain.song.SongNotFoundException;
import site.mutopia.server.domain.song.entity.SongEntity;
import site.mutopia.server.domain.song.repository.SongRepository;
import site.mutopia.server.domain.songComment.dto.SongCommentInfoResDto;
import site.mutopia.server.domain.songComment.dto.OrderBy;
import site.mutopia.server.domain.songComment.dto.SongCommentReqDto;
import site.mutopia.server.domain.songComment.entity.SongCommentEntity;
import site.mutopia.server.domain.songComment.repository.SongCommentRepository;
import site.mutopia.server.domain.user.entity.UserEntity;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SongCommentService {

    private final SongCommentRepository songCommentRepository;
    private final SongRepository songRepository;

    @Transactional(readOnly = true)
    public List<SongCommentInfoResDto> getUserSongComments(String userId, int page, UserEntity loggedInUser) {
        return songCommentRepository.findCommentsByUserId(userId, PageRequest.of(page, 20), loggedInUser != null ? loggedInUser.getId() : null);
    }

    @Transactional(readOnly = true)
    public SongCommentInfoResDto getSongComment(String userId, String songId, UserEntity loggedInUser) {
        return songCommentRepository.findSongCommentByUserIdAndSongId(userId, songId, loggedInUser != null ? loggedInUser.getId() : null);
    }

    public SongCommentEntity saveSongComment(UserEntity user, String songId, SongCommentReqDto dto) {

        SongEntity song = songRepository.findById(songId)
                .orElseThrow(() -> new SongNotFoundException(songId));

        SongCommentEntity comment = SongCommentEntity.builder()
                .writer(user)
                .song(song)
                .rating(dto.getRating())
                .comment(dto.getComment())
                .build();

        return songCommentRepository.save(comment);
    }

    @Transactional(readOnly = true)
    public List<SongCommentInfoResDto> getSongCommentBySongId(String songId, int offset, UserEntity loggedInUser, OrderBy orderBy) {
        return songCommentRepository.findSongCommentsBySongId(songId, PageRequest.of(offset, 20), loggedInUser != null ? loggedInUser.getId() : null, orderBy);
    }

    @Transactional(readOnly = true)
    public List<SongCommentInfoResDto> getRecentSongComment(int page, UserEntity loggedInUser) {
        return songCommentRepository.findCommentsOrderByCreatedAtDesc(PageRequest.of(page, 20), loggedInUser != null ? loggedInUser.getId() : null);
    }

    @Transactional(readOnly = true)
    public List<SongCommentInfoResDto> getSongCommentsByAlbumId(String albumId, int page, UserEntity loggedInUser, OrderBy orderBy) {
        return songCommentRepository.findCommentsByAlbumId(albumId, PageRequest.of(page, 20), loggedInUser != null ? loggedInUser.getId() : null, orderBy);
    }

}
