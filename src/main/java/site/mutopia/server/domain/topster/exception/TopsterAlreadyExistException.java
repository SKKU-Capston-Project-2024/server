package site.mutopia.server.domain.topster.exception;

import site.mutopia.server.global.error.exception.AlreadyExistException;

public class TopsterAlreadyExistException extends AlreadyExistException {

    public TopsterAlreadyExistException(String message) { super(message); }

    public TopsterAlreadyExistException(String message, Throwable cause) { super(message, cause); }

}
