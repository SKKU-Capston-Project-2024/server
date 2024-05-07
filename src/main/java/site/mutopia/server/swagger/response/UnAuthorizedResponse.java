package site.mutopia.server.swagger.response;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import site.mutopia.server.global.dto.ExceptionResponse;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@ApiResponse(responseCode = "401", description = "UnAuthorized", content = @Content(schema = @Schema(implementation =
        ExceptionResponse.class)))
public @interface UnAuthorizedResponse {
}