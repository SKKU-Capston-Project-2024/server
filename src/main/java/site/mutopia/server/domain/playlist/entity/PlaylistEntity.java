package site.mutopia.server.domain.playlist.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import site.mutopia.server.domain.user.entity.UserEntity;

@Entity
@Table(name = "playlist")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlaylistEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content", length = 10000)
    private String content;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private UserEntity creator;

    @CreatedDate
    private Long createdAt;

    @Builder
    public PlaylistEntity(String title, String content, UserEntity creator) {
        this.title = title;
        this.content = content;
        this.creator = creator;
    }
}