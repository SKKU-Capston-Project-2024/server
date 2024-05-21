package site.mutopia.server.domain.song.exception;

import site.mutopia.server.global.error.exception.EntityNotFoundException;

public class SongNotFoundException extends EntityNotFoundException {

    public SongNotFoundException(String message) { super(message); }

    public SongNotFoundException(String message, Throwable cause) { super(message, cause); }

}
