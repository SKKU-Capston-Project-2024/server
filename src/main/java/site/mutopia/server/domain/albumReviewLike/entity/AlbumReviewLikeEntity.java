package site.mutopia.server.domain.albumReviewLike.entity;

import jakarta.persistence.*;
import site.mutopia.server.domain.albumReview.entity.AlbumReviewEntity;
import site.mutopia.server.domain.user.entity.UserEntity;

@Entity
@Table(name = "album_review_like")
@IdClass(AlbumReviewLikeId.class)
public class AlbumReviewLikeEntity {
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Id
    @ManyToOne
    @JoinColumn(name = "review_id")
    private AlbumReviewEntity review;
}
