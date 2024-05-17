package site.mutopia.server.global.handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import site.mutopia.server.domain.auth.exception.UnAuthorizedException;
import site.mutopia.server.global.dto.ExceptionResponse;
import site.mutopia.server.global.error.exception.AlreadyExistException;
import site.mutopia.server.global.error.exception.EntityNotFoundException;

@RestControllerAdvice
@Slf4j
public class ControllerAdvice {

    String BAD_REQUEST_ERROR_MSG = "잘못된 요청입니다.";


    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionResponse> handleBadRequestException(BadRequestException exception) {
        log.error(exception.getClass().getName());
        log.error(exception.getMessage());
        return ResponseEntity.badRequest().body(new ExceptionResponse(BAD_REQUEST_ERROR_MSG));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionResponse> handleRuntimeException(RuntimeException exception) {
        log.error(exception.getClass().getName(), exception);
        return ResponseEntity.badRequest().body(new ExceptionResponse("Internal Server Error"));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleNotFoundException(EntityNotFoundException exception) {
        log.error(exception.getClass().getName());
        log.error(exception.getMessage());
        return ResponseEntity.status(404).body(new ExceptionResponse(exception.getMessage()));
    }

    @ExceptionHandler(UnAuthorizedException.class)
    public ResponseEntity<ExceptionResponse> handleAuthorizationException(UnAuthorizedException exception) {
        log.error(exception.getClass().getName());
        log.error(exception.getMessage());
        return ResponseEntity.status(401).body(new ExceptionResponse("인증에 실패했습니다."));
    }

    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<ExceptionResponse> handleAlreadyExistException(AlreadyExistException exception) {
        log.error(exception.getClass().getName());
        log.error(exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ExceptionResponse(exception.getMessage()));
    }
}
