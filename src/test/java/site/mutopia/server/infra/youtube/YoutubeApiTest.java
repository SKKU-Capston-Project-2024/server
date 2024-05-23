package site.mutopia.server.infra.youtube;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class YoutubeApiTest {

    @Autowired
    private YoutubeApi youtubeApi;

    @Test
    void test1() {
        String accessToken = "ya29.a0AXooCgsuARDNagfrXQk12QnkETHiorB5jXiQ_oHNtHdXl5NVTOs_Qi8OaGWfLkpRc-BhbcqI1p3VdsRM5omTye5fnMAm698W2BhTCx3FCBrY0KeBPhI87_JTG_THefMTr_HvXIb6ZGayuq-BDUm7QCHrIdpx-dTbnyiraCgYKAdgSARESFQHGX2MidBzwtEHQHmZw33bgImVH8g0171";

        JsonNode res = youtubeApi.savePlaylist(accessToken, "test-1", "description-1");
        System.out.println(res);
    }
}