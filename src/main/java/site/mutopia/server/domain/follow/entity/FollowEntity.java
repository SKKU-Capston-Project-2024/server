package site.mutopia.server.domain.follow.entity;

import jakarta.persistence.*;
import site.mutopia.server.domain.user.entity.UserEntity;

@Entity
@Table(name = "follow")
@IdClass(FollowId.class)
public class FollowEntity {

    // 복합키 매핑 @EmbeddedId vs @IdClass
    // https://www.inflearn.com/questions/1022839

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Id
    @ManyToOne
    @JoinColumn(name = "following_id")
    private UserEntity following;
}
