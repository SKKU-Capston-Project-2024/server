package site.mutopia.server.domain.songCommentLike.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.parameters.P;
import site.mutopia.server.domain.song.entity.SongEntity;
import site.mutopia.server.domain.songComment.entity.SongCommentEntity;
import site.mutopia.server.domain.user.entity.UserEntity;

import java.io.Serializable;

@Entity
@Table(name = "comment_like")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class SongCommentLikeEntity {

    @EmbeddedId
    private SongCommentLikeId id;

    @ManyToOne
    @JoinColumn(name = "song_id")
    @MapsId("songId")
    private SongEntity song;

    @ManyToOne
    @JoinColumn(name = "writer_id")
    @MapsId("writerId")
    private UserEntity writer;


    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "song_id", referencedColumnName = "song_id", insertable = false, updatable = false),
            @JoinColumn(name = "writer_id", referencedColumnName = "writer_id", insertable = false, updatable = false)
    })
    private SongCommentEntity songComment;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @MapsId("likeUserId")
    private UserEntity likeUser;
}
