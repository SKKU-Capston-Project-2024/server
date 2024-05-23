package site.mutopia.server.domain.youtubePlaylist.service;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.mutopia.server.domain.auth.repository.GoogleTokenRepository;
import site.mutopia.server.domain.song.entity.SongEntity;
import site.mutopia.server.domain.song.repository.SongRepository;
import site.mutopia.server.infra.youtube.YoutubeApi;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class YoutubePlaylistService {
    private final YoutubePlaylistCRUDService crudService;
    private final YoutubeApi youtubeApi;
    private final SongRepository songRepository;
    private final GoogleTokenRepository googleTokenRepository;

    public String exportPlaylistToYoutube(String userId, Long playlistId, String title, String description) {
        List<SongEntity> songs = songRepository.findSongsByPlaylistId(playlistId);

        // TODO: 에러 정의
        String accessToken = googleTokenRepository.findAccessTokenByUserId(userId).orElseThrow(() -> new IllegalStateException("User Does not have access token"));
        JsonNode savePlaylistRes = youtubeApi.savePlaylist(accessToken, title, description);
        String youtubePlaylistId = savePlaylistRes.path("id").asText();

        List<CompletableFuture<String>> videoIdFutures = songs.stream()
                .map(song -> CompletableFuture.supplyAsync(() -> {
                    JsonNode video = youtubeApi.getFirstVideo(song.getTitle() + " - " + song.getAlbum().getArtistName());
                    return video.path("items").get(0).path("id").path("videoId").asText();
                }))
                .toList();

        List<String> videoIds = videoIdFutures.stream()
                .map(CompletableFuture::join)
                .toList();

        List<CompletableFuture<JsonNode>> addVideoFutures = videoIds.stream()
                .map(videoId -> youtubeApi.addVideoToPlaylistAsync(accessToken, youtubePlaylistId, videoId))
                .toList();

        addVideoFutures.forEach(CompletableFuture::join);

        crudService.savePlaylist(youtubePlaylistId, userId);

        return youtubePlaylistId;
    }
}
