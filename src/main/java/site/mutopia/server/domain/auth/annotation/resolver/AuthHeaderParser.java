package site.mutopia.server.domain.auth.annotation.resolver;

import jakarta.servlet.http.HttpServletRequest;
import site.mutopia.server.domain.auth.exception.UnAuthorizedException;


public class AuthHeaderParser {

    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer ";

    private AuthHeaderParser() {

    }

    public static String parse(HttpServletRequest request) {
        final String authorizationHeader = request.getHeader(AUTHORIZATION);

        if (authorizationHeader != null && authorizationHeader.startsWith(BEARER)) {
            return authorizationHeader.substring(BEARER.length());
        }

        return null;
    }
}

