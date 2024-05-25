package site.mutopia.server.spotify.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import site.mutopia.server.domain.auth.annotation.LoginUser;
import site.mutopia.server.domain.user.entity.UserEntity;
import site.mutopia.server.spotify.SpotifyClientManager;
import site.mutopia.server.spotify.SpotifyClientManager.AuthResponse;
import site.mutopia.server.spotify.dto.login.SpotifyLoginResDto;
import site.mutopia.server.spotify.service.SpotifyService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class SpotifyController {
    private static final String STATE_KEY = "spotify_auth_state";

    private final SpotifyClientManager spotifyClientManager;
    private final SpotifyService spotifyService;

    @GetMapping("/spotify/login")
    public ResponseEntity<SpotifyLoginResDto> login(@LoginUser UserEntity loggedInUser, HttpServletResponse response) {
        String state = loggedInUser.getId();
        String scope = "user-read-private user-read-email playlist-read-private playlist-modify-private";
        String authUrl = "https://accounts.spotify.com/authorize?" +
                "response_type=code&client_id=" + spotifyClientManager.getClientId() + "&scope=" + scope +
                "&redirect_uri=" + spotifyClientManager.getRedirectUri() + "&state=" + state;

        response.addCookie(new Cookie(STATE_KEY, state));

        return ResponseEntity.ok().body(SpotifyLoginResDto.builder().redirectUrl(authUrl).build());
    }

    @GetMapping("/spotify/login/callback")
    public ResponseEntity<String> callback(
            @RequestParam(value = "code", required = false) String code,
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "state") String userId,
            HttpServletRequest request) {

        if (error != null) {
            // TODO: 에러 정의
            log.error("Spotify Login Callback Error: {}", error);
            throw new IllegalStateException("Spotify Login Callback Error");
        }

        AuthResponse tokenRes = spotifyClientManager.exchangeCodeForToken(code);

        spotifyService.saveSpotifyToken(userId, tokenRes.getAccessToken(), tokenRes.getRefreshToken());
        log.debug("Spotify tokens saved for userId: {}, sessionId: {}", userId, request.getSession().getId());

        return ResponseEntity.ok().body(tokenRes.getAccessToken());
    }
}
