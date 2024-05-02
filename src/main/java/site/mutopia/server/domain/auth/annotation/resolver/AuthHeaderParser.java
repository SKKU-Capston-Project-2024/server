package site.mutopia.server.domain.auth.annotation.resolver;

import jakarta.servlet.http.HttpServletRequest;


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

        throw new IllegalArgumentException("요청에서 Authorization Bearer 헤더를 찾을 수 없습니다.");
    }
}

