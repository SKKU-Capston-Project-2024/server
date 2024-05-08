package site.mutopia.server.domain.albumRating.exception;

public class AlbumRatingNotFoundException extends RuntimeException {
    public AlbumRatingNotFoundException(String message) {
        super(message);
    }
}
