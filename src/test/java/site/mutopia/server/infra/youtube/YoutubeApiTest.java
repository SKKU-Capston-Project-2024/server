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
        JsonNode res = youtubeApi.getPlaylists("ya29.a0AXooCgt_Y1i-1Gy3Xxc3plQrSngZTBnXaiRQxuxEpPL5hFHW7_bF7kreCc5SeElUHZ5CGiaxX0SPITfVRotRcnwpUXHaoVOahCdlpiyKj-d4hGUvjWBvSv_PggRFzMGQ3l5J2qjS4SM4JV7uTYQqKMLvVBLkh7MC9yKCaCgYKAegSARESFQHGX2Miaz1S914zHIOZbtfDx9bNvw0171");
        System.out.println(res);
    }
}