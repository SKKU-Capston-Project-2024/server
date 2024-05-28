package site.mutopia.server.domain.songComment.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import site.mutopia.server.domain.song.entity.SongEntity;
import site.mutopia.server.domain.user.entity.UserEntity;

@Entity
@Table(name = "song_comment")
@IdClass(SongCommentId.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Builder
public class SongCommentEntity {

    @Id
    @ManyToOne
    @JoinColumn(name = "writer_id")
    @MapsId("writer")
    private UserEntity writer;

    @Id
    @ManyToOne
    @JoinColumn(name = "song_id")
    @MapsId("song")
    private SongEntity song;

    @Column(name = "rating")
    private Integer rating;

    @Column(name = "comment")
    private String comment;

    @CreatedDate
    private Long createdAt;
}
