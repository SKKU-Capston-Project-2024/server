package site.mutopia.server.global.error.exception;

// TODO: Exception 정의
public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}
