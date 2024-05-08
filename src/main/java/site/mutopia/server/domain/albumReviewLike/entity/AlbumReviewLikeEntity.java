package site.mutopia.server.domain.albumReviewLike.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import site.mutopia.server.domain.albumReview.entity.AlbumReviewEntity;
import site.mutopia.server.domain.user.entity.UserEntity;

@Entity
@Table(name = "album_review_like")
@IdClass(AlbumReviewLikeId.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class AlbumReviewLikeEntity {
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    @MapsId("user")
    private UserEntity user;

    @Id
    @ManyToOne
    @JoinColumn(name = "review_id")
    @MapsId("review")
    private AlbumReviewEntity review;
}
