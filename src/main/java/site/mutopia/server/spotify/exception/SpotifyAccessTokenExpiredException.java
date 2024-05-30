package site.mutopia.server.spotify.exception;

public class SpotifyAccessTokenExpiredException extends RuntimeException{
    public SpotifyAccessTokenExpiredException(String message) {
        super(message);
    }

    public SpotifyAccessTokenExpiredException(String message, Throwable cause) {
        super(message, cause);
    }
}
