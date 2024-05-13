package site.mutopia.server.domain.album.exception;

import site.mutopia.server.global.error.exception.EntityNotFoundException;

public class AlbumNotFoundException extends EntityNotFoundException {
    public AlbumNotFoundException(String message) {
        super(message);
    }
}
