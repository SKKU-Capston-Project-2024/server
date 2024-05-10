package site.mutopia.server.domain.songComment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.mutopia.server.domain.songComment.dto.SongCommentInfoResDto;
import site.mutopia.server.domain.songComment.repository.SongCommentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SongCommentService {
    private final SongCommentRepository songCommentRepository;

    @Transactional(readOnly = true)
    public List<SongCommentInfoResDto> getUserSongComments(String userId, int limit) {
        Page<SongCommentInfoResDto> songComments = songCommentRepository.findCommentsByUserId(userId, PageRequest.of(0, limit));
        return songComments.getContent();
    }
}
