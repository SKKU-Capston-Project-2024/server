package site.mutopia.server.domain.playlist.exception;

import site.mutopia.server.global.error.exception.EntityNotFoundException;

public class PlaylistNotFoundException extends EntityNotFoundException {

    public PlaylistNotFoundException(String message) { super(message); }

    public PlaylistNotFoundException(String message, Throwable cause) { super(message, cause); }

}
