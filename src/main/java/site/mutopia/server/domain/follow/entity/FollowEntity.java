package site.mutopia.server.domain.follow.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import site.mutopia.server.domain.user.entity.UserEntity;

@Entity
@Table(name = "follow")
@IdClass(FollowId.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class FollowEntity {

    // 복합키 매핑 @EmbeddedId vs @IdClass
    // https://www.inflearn.com/questions/1022839

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    @MapsId("user")
    private UserEntity user;

    @Id
    @ManyToOne
    @JoinColumn(name = "following_id")
    @MapsId("following")
    private UserEntity following;
}
