package site.mutopia.server.domain.songCommentLike.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import site.mutopia.server.domain.songCommentLike.entity.SongCommentLikeEntity;
import site.mutopia.server.domain.songCommentLike.entity.SongCommentLikeId;

import java.util.List;

@Repository
public interface SongCommentLikeRepository extends JpaRepository<SongCommentLikeEntity, SongCommentLikeId>, SongCommentLikeCustomRepository {
}
