package site.mutopia.server.domain.songLike.repository;

public interface SongLikeCustomRepository {
    boolean existsBySongIdAndUserId(String songId, String userId);
    void deleteBySongIdAndUserId(String songId, String userId);
}
