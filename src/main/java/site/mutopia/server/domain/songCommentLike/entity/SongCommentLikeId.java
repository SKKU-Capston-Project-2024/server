package site.mutopia.server.domain.songCommentLike.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import site.mutopia.server.domain.songComment.entity.SongCommentEntity;
import site.mutopia.server.domain.songComment.entity.SongCommentId;
import site.mutopia.server.domain.user.entity.UserEntity;

import java.io.Serializable;

@AllArgsConstructor
@Embeddable
@NoArgsConstructor
@EqualsAndHashCode
public class SongCommentLikeId implements Serializable {
    private SongCommentId songCommentId;
    private String likeUserId;
}
