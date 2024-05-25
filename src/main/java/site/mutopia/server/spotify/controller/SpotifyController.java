package site.mutopia.server.spotify.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.WebUtils;
import site.mutopia.server.spotify.SpotifyClientManager;
import site.mutopia.server.spotify.SpotifyClientManager.AuthResponse;

import java.security.SecureRandom;
import java.util.Base64;

@Controller
@RequiredArgsConstructor
public class SpotifyController {
    private static final String STATE_KEY = "spotify_auth_state";

    private final SpotifyClientManager spotifyClientManager;

    @GetMapping("/spotify-login")
    public String showSpotifyLoginPage() {
        return "spotify-login";
    }

    @GetMapping("/spotify/login")
    public String login(HttpServletResponse response) {
        String state = generateRandomString(16);
        String scope = "user-read-private user-read-email playlist-read-private playlist-modify-private";
        String authUrl = "https://accounts.spotify.com/authorize?" +
                "response_type=code&client_id=" + spotifyClientManager.getClientId() + "&scope=" + scope +
                "&redirect_uri=" + spotifyClientManager.getRedirectUri() + "&state=" + state;

        response.addCookie(new Cookie(STATE_KEY, state));
        return "redirect:" + authUrl;
    }

    @GetMapping("/spotify/login/callback")
    public String callback(@RequestParam(value = "code", required = false) String code, @RequestParam(value = "error", required = false) String error, @RequestParam(value = "state", required = false) String state, HttpServletRequest request, HttpServletResponse response, Model model) {
        Cookie stateCookie = WebUtils.getCookie(request, STATE_KEY);

        if (state == null || stateCookie == null || !state.equals(stateCookie.getValue()) || error != null) {
            model.addAttribute("error", "State mismatch");
            return "spotify-login-error";
        }

        stateCookie.setMaxAge(0);
        response.addCookie(stateCookie);

        // Exchange code for access token
        AuthResponse tokenRes = spotifyClientManager.exchangeCodeForToken(code);
        System.out.println(tokenRes);

        model.addAttribute("access_token", tokenRes.getAccessToken());
        model.addAttribute("refresh_token", tokenRes.getRefreshToken());

        return "spotify-login-success";
    }

    private String generateRandomString(int length) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[length];
        secureRandom.nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }
}
