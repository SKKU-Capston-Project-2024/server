package site.mutopia.server.domain.songComment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.mutopia.server.domain.song.SongNotFoundException;
import site.mutopia.server.domain.song.entity.SongEntity;
import site.mutopia.server.domain.song.repository.SongRepository;
import site.mutopia.server.domain.songComment.dto.SongCommentInfoResDto;
import site.mutopia.server.domain.songComment.dto.SongCommentReqDto;
import site.mutopia.server.domain.songComment.entity.SongCommentEntity;
import site.mutopia.server.domain.songComment.entity.SongCommentId;
import site.mutopia.server.domain.songComment.exception.SongCommentNotFoundException;
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
    public List<SongCommentInfoResDto> getUserSongComments(String userId, int page) {
        return songCommentRepository.findCommentsByUserId(userId, PageRequest.of(page, 20)).getContent().stream()
                .map(SongCommentInfoResDto::fromEntity)
                .toList();
    }

    public SongCommentInfoResDto getSongComment(String userId, String songId) {
        SongCommentEntity comment = songCommentRepository.findById(new SongCommentId(userId, songId))
                .orElseThrow(() -> new SongCommentNotFoundException(userId, songId));

        return SongCommentInfoResDto.fromEntity(comment);

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

    public List<SongCommentInfoResDto> getSongCommentBySongId(String songId, int offset) {
        return songCommentRepository.findCommentsOrderByCreatedAtDesc(songId, PageRequest.of(offset, 20)).getContent().stream()
                .map(SongCommentInfoResDto::fromEntity)
                .toList();
    }

    public List<SongCommentInfoResDto> getRecentSongComment(int page) {
        return songCommentRepository.findCommentsOrderByCreatedAtDesc(PageRequest.of(page, 20)).getContent().stream()
                .map(SongCommentInfoResDto::fromEntity)
                .toList();
    }

    public List<SongCommentInfoResDto> getRecentSongCommentByAlbum(String albumId, int page) {
        return songCommentRepository.findCommentsByAlbumIdOrderByCreatedAtDesc(albumId, PageRequest.of(page, 20)).getContent()
                .stream()
                .map(SongCommentInfoResDto::fromEntity)
                .toList();
    }


}
