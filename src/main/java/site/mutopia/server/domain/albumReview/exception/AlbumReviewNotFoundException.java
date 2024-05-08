package site.mutopia.server.domain.albumReview.exception;

public class AlbumReviewNotFoundException extends RuntimeException {
    public AlbumReviewNotFoundException(String message) {
        super(message);
    }
}
