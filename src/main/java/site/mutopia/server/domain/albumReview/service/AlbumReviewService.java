package site.mutopia.server.domain.albumReview.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.mutopia.server.domain.album.entity.AlbumEntity;
import site.mutopia.server.domain.album.exception.AlbumNotFoundException;
import site.mutopia.server.domain.album.repository.AlbumRepository;
import site.mutopia.server.domain.albumReview.dto.AlbumReviewSaveDto;
import site.mutopia.server.domain.albumReview.entity.AlbumReviewEntity;
import site.mutopia.server.domain.albumReview.repository.AlbumReviewRepository;
import site.mutopia.server.domain.user.entity.UserEntity;
import site.mutopia.server.domain.user.exception.UserNotFoundException;
import site.mutopia.server.domain.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AlbumReviewService {

    private final AlbumReviewRepository albumReviewRepository;
    private final UserRepository userRepository;
    private final AlbumRepository albumRepository;

    public AlbumReviewEntity saveAlbumReview(String writerId, AlbumReviewSaveDto reviewSaveDto) {
        UserEntity writer = userRepository.findById(writerId).orElseThrow(() -> new UserNotFoundException("User not found with ID: " + writerId));
        AlbumEntity album = albumRepository.findAlbumById(reviewSaveDto.getAlbumId()).orElseThrow(() -> new AlbumNotFoundException("Album not found. albumId: " + reviewSaveDto.getAlbumId() + " does not exist."));
        AlbumReviewEntity albumReview = reviewSaveDto.toEntity(writer, album);

        return albumReviewRepository.save(albumReview);
    }
}
