package site.mutopia.server.spotify.exception;

import site.mutopia.server.global.error.exception.EntityNotFoundException;

public class SpotifyRefreshTokenNotFoundException extends EntityNotFoundException {

    public SpotifyRefreshTokenNotFoundException(String message) {
        super(message);
    }

    public SpotifyRefreshTokenNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
