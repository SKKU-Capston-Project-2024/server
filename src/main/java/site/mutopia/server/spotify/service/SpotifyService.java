package site.mutopia.server.spotify.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.mutopia.server.domain.user.entity.UserEntity;
import site.mutopia.server.domain.user.exception.UserNotFoundException;
import site.mutopia.server.domain.user.repository.UserRepository;
import site.mutopia.server.spotify.SpotifyClientManager;
import site.mutopia.server.spotify.dto.playlist.SpotifyPlaylist;
import site.mutopia.server.spotify.dto.playlist.SpotifyPlaylistDetails;
import site.mutopia.server.spotify.dto.recommendation.RecommendationsDto;
import site.mutopia.server.spotify.entity.SpotifyTokenEntity;
import site.mutopia.server.spotify.entity.SpotifyTokenType;
import site.mutopia.server.spotify.exception.SpotifyAccessTokenNotFoundException;
import site.mutopia.server.spotify.repository.SpotifyTokenRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SpotifyService {
    private final SpotifyTokenRepository spotifyTokenRepository;
    private final UserRepository userRepository;
    private final SpotifyClientManager spotifyClientManager;

    @Transactional
    public void saveToken(String userId, String spotifyUserId, String accessToken, String refreshToken) {
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found. userId: " + userId + " does not exist."));

        LocalDateTime now = LocalDateTime.now();

        SpotifyTokenEntity accessTokenEntity = SpotifyTokenEntity.builder()
                .user(user)
                .tokenType(SpotifyTokenType.ACCESS)
                .tokenValue(accessToken)
                .issuedAt(now)
                .spotifyUserId(spotifyUserId)
                .build();

        SpotifyTokenEntity refreshTokenEntity = SpotifyTokenEntity.builder()
                .user(user)
                .tokenType(SpotifyTokenType.REFRESH)
                .tokenValue(refreshToken)
                .issuedAt(now)
                .spotifyUserId(spotifyUserId)
                .build();

        spotifyTokenRepository.saveAll(List.of(accessTokenEntity, refreshTokenEntity));
    }

    @Transactional
    public void updateAccessToken(String userId, String accessTokenValue) {
        SpotifyTokenEntity accessToken = spotifyTokenRepository.findByUserIdAndTokenType(userId, SpotifyTokenType.ACCESS)
                .orElseThrow(() -> new SpotifyAccessTokenNotFoundException("userId: " + userId + "doesn't have access token"));

        accessToken.updateAccessToken(accessTokenValue);
    }

    public String refreshAccessToken(String refreshToken) {
        return spotifyClientManager.refreshAccessToken(refreshToken);
    }

    public String createPlaylist(SpotifyTokenEntity spotifyToken, String playlistName, String playlistDescription) {
        SpotifyPlaylist playlist = spotifyClientManager.createPlaylist(spotifyToken.getSpotifyUserId(), spotifyToken.getTokenValue(), playlistName, playlistDescription, true);

        return playlist.getId();
    }

    public void addSongsToPlaylist(SpotifyTokenEntity spotifyToken, String spotifyPlaylistId, List<String> songIds) {
        spotifyClientManager.addTracksToPlaylist(spotifyPlaylistId, spotifyToken.getTokenValue(), songIds, 0);
    }

    public SpotifyPlaylistDetails getPlaylistDetails(SpotifyTokenEntity spotifyAccessToken, String spotifyPlaylistId) {
        return spotifyClientManager.getPlaylistDetails(spotifyPlaylistId, spotifyAccessToken.getTokenValue());
    }

    public RecommendationsDto getRecommendations(List<String> songIds, String accessToken) {
        return spotifyClientManager.getRecommendations(songIds, accessToken);
    }
}
