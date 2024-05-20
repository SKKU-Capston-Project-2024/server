package site.mutopia.server.domain.follow;

import site.mutopia.server.global.error.exception.EntityNotFoundException;

public class FollowingNotFoundException extends EntityNotFoundException {
    public FollowingNotFoundException(String follower, String following) {
        super("Following not found: follower=" + follower + ", following=" + following);
    }
}
