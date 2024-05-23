package site.mutopia.server.domain.youtubePlaylist.service;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.mutopia.server.domain.auth.repository.GoogleTokenRepository;
import site.mutopia.server.domain.song.entity.SongEntity;
import site.mutopia.server.domain.song.repository.SongRepository;
import site.mutopia.server.infra.youtube.YoutubeApi;

import java.util.List;

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

        // TODO: youtubeApi 비동기로 성능개선
        songs.forEach(song -> {
            JsonNode video = youtubeApi.getFirstVideo(song.getTitle() + " - " + song.getAlbum().getArtistName());
            String videoId = video.path("items").get(0).path("id").path("videoId").asText();
            youtubeApi.addVideoToPlaylist(accessToken, youtubePlaylistId, videoId);
        });

        crudService.savePlaylist(youtubePlaylistId, userId);

        return youtubePlaylistId;
    }
}
