package site.mutopia.server.domain.topster.exception;

public class TopsterNotFoundException extends RuntimeException {
    public TopsterNotFoundException(String message) {
        super(message);
    }
}
