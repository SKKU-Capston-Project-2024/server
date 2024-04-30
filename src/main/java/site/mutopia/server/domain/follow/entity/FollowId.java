package site.mutopia.server.domain.follow.entity;

import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode
public class FollowId implements Serializable {

    // If user1(id:1) follows user2(id:2), follow(userId:1, followingId:2)
    private Long userId;
    private Long followingId;
}
