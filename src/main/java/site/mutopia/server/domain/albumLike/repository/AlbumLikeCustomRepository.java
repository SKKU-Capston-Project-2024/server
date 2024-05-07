package site.mutopia.server.domain.albumLike.repository;

public interface AlbumLikeCustomRepository {
    boolean existsByAlbumIdAndUserId(String albumId, String userId);
    void deleteByAlbumIdAndUserId(String albumId, String userId);
}
