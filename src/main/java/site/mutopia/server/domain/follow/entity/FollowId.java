package site.mutopia.server.domain.follow.entity;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@EqualsAndHashCode
public class FollowId implements Serializable {

    // If user1(id:1) follows user2(id:2), follow(userId:1, followingId:2)
    private String user;
    private String following;
}
