package site.mutopia.server.spotify.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.mutopia.server.domain.user.entity.UserEntity;
import site.mutopia.server.domain.user.exception.UserNotFoundException;
import site.mutopia.server.domain.user.repository.UserRepository;
import site.mutopia.server.spotify.entity.SpotifyTokenEntity;
import site.mutopia.server.spotify.entity.SpotifyTokenType;
import site.mutopia.server.spotify.repository.SpotifyTokenRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SpotifyService {
    private final SpotifyTokenRepository spotifyTokenRepository;
    private final UserRepository userRepository;

    public void saveSpotifyToken(String userId, String accessToken, String refreshToken) {
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found. userId: " + userId + " does not exist."));

        LocalDateTime now = LocalDateTime.now();

        SpotifyTokenEntity accessTokenEntity = SpotifyTokenEntity.builder()
                .user(user)
                .tokenType(SpotifyTokenType.ACCESS)
                .tokenValue(accessToken)
                .issuedAt(now)
                .build();

        SpotifyTokenEntity refreshTokenEntity = SpotifyTokenEntity.builder()
                .user(user)
                .tokenType(SpotifyTokenType.REFRESH)
                .tokenValue(refreshToken)
                .issuedAt(now)
                .build();

        spotifyTokenRepository.saveAll(List.of(accessTokenEntity, refreshTokenEntity));
    }
}
