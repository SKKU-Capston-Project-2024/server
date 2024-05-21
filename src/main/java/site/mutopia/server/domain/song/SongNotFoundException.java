package site.mutopia.server.domain.song;

import site.mutopia.server.global.error.exception.EntityNotFoundException;

public class SongNotFoundException extends EntityNotFoundException {
    public SongNotFoundException(String songId) {
        super("Song not found: " + songId);
    }
}
