package site.mutopia.server.domain.playlist.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import site.mutopia.server.domain.playlistSong.entity.PlaylistSongEntity;
import site.mutopia.server.domain.user.entity.UserEntity;

import java.util.List;

@Entity
@Table(name = "playlist")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
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

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "playlist_id")
    private List<PlaylistSongEntity> songs;

    @CreatedDate
    private Long createdAt;

    @Builder
    public PlaylistEntity(String title, String content, UserEntity creator) {
        this.title = title;
        this.content = content;
        this.creator = creator;
    }

    public void modify(String title, String content) {
        this.title = title;
        this.content = content;
    }
}