package site.mutopia.server.domain.songComment.entity;

import jakarta.persistence.*;
import site.mutopia.server.domain.song.entity.SongEntity;
import site.mutopia.server.domain.user.entity.UserEntity;

@Entity
@Table(name = "song_comment")
@IdClass(SongCommentId.class)
public class SongCommentEntity {
    @Id
    @ManyToOne
    @JoinColumn(name = "writer_id")
    private UserEntity writer;

    @Id
    @ManyToOne
    @JoinColumn(name = "song_id")
    private SongEntity song;

    @Column(name = "rating")
    private Integer rating;

    @Column(name = "comment")
    private String comment;
}
