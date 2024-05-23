package site.mutopia.server.domain.songLike.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import site.mutopia.server.domain.songLike.entity.SongLikeEntity;
import site.mutopia.server.domain.songLike.entity.SongLikeId;
import site.mutopia.server.domain.songLike.repository.SongLikeCustomRepository;

import java.util.List;

@Repository
public interface SongLikeRepository extends JpaRepository<SongLikeEntity, SongLikeId>, SongLikeCustomRepository {

    @Query("select count(songLike) from SongLikeEntity songLike where songLike.song.id = :songId")
    Long countLikesBySongId(String songId);

    @Query("select sl from SongLikeEntity sl JOIN FETCH sl.song JOIN FETCH sl.song.album WHERE sl.user.id = :userId")
    List<SongLikeEntity> findLikedSongsByUserId(@Param("userId") String userId, Pageable pageable);

}
