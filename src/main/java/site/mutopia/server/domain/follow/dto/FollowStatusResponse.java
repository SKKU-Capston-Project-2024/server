package site.mutopia.server.domain.follow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class FollowStatusResponse {
    private boolean isFollowing;
}
