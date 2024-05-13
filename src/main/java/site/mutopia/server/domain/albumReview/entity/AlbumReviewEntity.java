package site.mutopia.server.domain.albumReview.entity;

import jakarta.persistence.*;
import lombok.*;
import site.mutopia.server.domain.user.entity.UserEntity;
import site.mutopia.server.domain.album.entity.AlbumEntity;

@Entity
@Table(name = "album_review")
@Getter
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

    @ManyToOne
    @JoinColumn(name = "writer_id")
    private UserEntity writer;

    @ManyToOne
    @JoinColumn(name = "album_id")
    private AlbumEntity album;


    @Builder
    public AlbumReviewEntity(String title, String content, Integer rating, UserEntity writer, AlbumEntity album) {
        this.title = title;
        this.content = content;
        this.rating = rating;
        this.writer = writer;
        this.album = album;
    }
}

