package site.mutopia.server.domain.songComment.exception;

import site.mutopia.server.global.error.exception.EntityNotFoundException;

public class SongCommentNotFoundException extends EntityNotFoundException {

    public SongCommentNotFoundException(String userId, String songId) {
        super("Song Comment Not Found. userId: " + userId + ", songId: " + songId);
    }
}
