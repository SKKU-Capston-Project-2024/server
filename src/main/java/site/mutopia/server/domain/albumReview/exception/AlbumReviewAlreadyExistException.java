package site.mutopia.server.domain.albumReview.exception;

import site.mutopia.server.global.error.exception.AlreadyExistException;

public class AlbumReviewAlreadyExistException extends AlreadyExistException {

    public AlbumReviewAlreadyExistException(String message) {
        super(message);
    }

    public AlbumReviewAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

}
