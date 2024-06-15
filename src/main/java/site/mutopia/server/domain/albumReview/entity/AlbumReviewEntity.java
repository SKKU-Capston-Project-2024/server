package site.mutopia.server.domain.albumReview.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Formula;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import site.mutopia.server.domain.user.entity.UserEntity;
import site.mutopia.server.domain.album.entity.AlbumEntity;

@Entity
@Table(name = "album_review")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AlbumReviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    // @Lob 어노테이션은 조회시 성능이슈 때문에 @Column 사용
    @Column(name = "content", length = 10000)
    private String content;

    @Column(name = "rating")
    private Integer rating;

    @Column(name = "pinned")
    private Boolean pinned;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    private UserEntity writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album_id")
    private AlbumEntity album;

    @CreatedDate
    private Long createdAt;

    @Formula("(SELECT COUNT(*) FROM album_review_like arl WHERE arl.review_id = id)")
    private Long likeCount;

    @Builder
    public AlbumReviewEntity(String title, String content, Integer rating, UserEntity writer, AlbumEntity album) {
        this.title = title;
        this.content = content;
        this.rating = rating;
        this.pinned = false;
        this.writer = writer;
        this.album = album;
    }

    public void pin() {
        this.pinned = true;
    }
}

