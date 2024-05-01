package site.mutopia.server.domain.albumReview.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "album_review")
public class AlbumReviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "album_review_id")
    private Long albumReviewId;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "rating")
    private Integer rating;

    @Column(name = "like_count")
    private Integer likeCount;

    // TODO: set relationships with other entities (Writer(UserEntity), Album)
}

