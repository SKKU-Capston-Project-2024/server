package site.mutopia.server.domain.albumReviewLike.repository;

public interface AlbumReviewLikeCustomRepository {
    boolean existsByAlbumReviewIdAndUserId(Long albumReviewId, String userId);
    void deleteByAlbumReviewIdAndUserId(Long albumReviewId, String userId);
}
