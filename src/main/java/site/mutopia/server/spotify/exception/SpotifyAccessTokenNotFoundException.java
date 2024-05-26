package site.mutopia.server.spotify.exception;

import site.mutopia.server.global.error.exception.EntityNotFoundException;

public class SpotifyAccessTokenNotFoundException extends EntityNotFoundException {

    public SpotifyAccessTokenNotFoundException(String message) {
        super(message);
    }

    public SpotifyAccessTokenNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
