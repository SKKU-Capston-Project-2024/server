package site.mutopia.server.infra.youtube;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import site.mutopia.server.domain.youtubePlaylist.service.YoutubePlaylistService;

@SpringBootTest
class YoutubeApiTest {

    @Autowired
    private YoutubeApi youtubeApi;

    @Autowired
    private YoutubePlaylistService youtubePlaylistService;

    @Test
    void test1() {
        JsonNode res = youtubeApi.getFirstVideo("playboi carti - magnolia");
        System.out.println(res);
    }

    @Test
    void test2() {
        youtubePlaylistService.exportPlaylistToYoutube("84102e90-8d58-4b6e-9b44-6ceb6b984c59", 12L, "test-playlist-1", "테스트임");
    }
}