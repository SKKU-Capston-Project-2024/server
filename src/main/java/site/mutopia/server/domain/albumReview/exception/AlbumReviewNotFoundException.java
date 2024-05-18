package site.mutopia.server.domain.albumReview.exception;

import site.mutopia.server.global.error.exception.EntityNotFoundException;

public class AlbumReviewNotFoundException extends EntityNotFoundException {

    public AlbumReviewNotFoundException(String message) {
        super(message);
    }

    public AlbumReviewNotFoundException(String message, Throwable cause) { super(message, cause); }

}
