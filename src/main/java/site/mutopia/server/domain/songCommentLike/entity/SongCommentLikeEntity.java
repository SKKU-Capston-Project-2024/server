package site.mutopia.server.domain.songCommentLike.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.security.core.parameters.P;
import site.mutopia.server.domain.song.entity.SongEntity;
import site.mutopia.server.domain.songComment.entity.SongCommentEntity;
import site.mutopia.server.domain.user.entity.UserEntity;

import java.io.Serializable;

@Entity
@Table(name = "comment_like")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@OnDelete(action = OnDeleteAction.CASCADE)
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SongCommentLikeEntity {

    @EmbeddedId
    private SongCommentLikeId id;

    @MapsId("songCommentId")
    @ManyToOne(targetEntity = SongCommentEntity.class, fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "comment_song_id", referencedColumnName = "song_id"),
            @JoinColumn(name = "comment_writer_id", referencedColumnName = "writer_id")
    })
    private SongCommentEntity songComment;

    @ManyToOne(targetEntity = UserEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @MapsId("likeUserId")
    private UserEntity likeUser;
}
