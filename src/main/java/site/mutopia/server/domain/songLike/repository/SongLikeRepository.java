package site.mutopia.server.domain.songLike.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import site.mutopia.server.domain.songLike.entity.SongLikeEntity;
import site.mutopia.server.domain.songLike.entity.SongLikeId;
import site.mutopia.server.domain.songLike.repository.SongLikeCustomRepository;

@Repository
public interface SongLikeRepository extends JpaRepository<SongLikeEntity, SongLikeId>, SongLikeCustomRepository {

    @Query("select count(songLike) from SongLikeEntity songLike where songLike.song.id = :songId")
    Long countLikesBySongId(String songId);

}
