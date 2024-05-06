package site.mutopia.server.global.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import se.michaelthelin.spotify.exceptions.detailed.BadRequestException;
import se.michaelthelin.spotify.exceptions.detailed.NotFoundException;
import site.mutopia.server.domain.auth.exception.UnAuthorizedException;
import site.mutopia.server.global.dto.ExceptionResponse;

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
        log.error(exception.getClass().getName());
        log.error(exception.getMessage());
        return ResponseEntity.badRequest().body(new ExceptionResponse(BAD_REQUEST_ERROR_MSG));
    }


    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleNotFoundException(NotFoundException exception) {
        log.error(exception.getClass().getName());
        log.error(exception.getMessage());
        return ResponseEntity.badRequest().body(new ExceptionResponse("Resource not found"));
    }

    @ExceptionHandler(UnAuthorizedException.class)
    public ResponseEntity<ExceptionResponse> handleAuthorizationException(UnAuthorizedException exception) {
        log.error(exception.getClass().getName());
        log.error(exception.getMessage());
        return ResponseEntity.status(401).body(new ExceptionResponse("인증에 실패했습니다."));
    }



}
