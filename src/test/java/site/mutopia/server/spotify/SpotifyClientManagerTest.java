package site.mutopia.server.spotify;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import site.mutopia.server.spotify.dto.playlist.SpotifyPlaylist;
import site.mutopia.server.spotify.dto.playlist.SpotifyPlaylistAddTracksRes;
import site.mutopia.server.spotify.dto.profile.SpotifyUserProfile;
import site.mutopia.server.spotify.dto.recommendation.RecommendationsDto;

import java.util.List;

@SpringBootTest
class SpotifyClientManagerTest {

    @Autowired
    SpotifyClientManager client;

    @Test
    void getCurrentUserProfile() {
        SpotifyUserProfile profile = client.getCurrentUserProfile("BQC27DdcNt8a2RHlA3mllivXha8SLIHnBoREfvZNhjo68LogYbfxhn8YqC7Ig7MhcijNLNGImUm2rvN2_OJ8QXpeJnMJ-OBCW2sKG-5aCF4mOSDB6L3UjgEW2BUALMnvQNrCrn0Ts0Rs-rnFfQVh6iPBV7t13jh7YIETfo820VO8XJE5z_Ue5KtR0Cszb29Lmb9vzsxFKb2nvGwMktt-_WNKJdC_Erb5y3W-0RQ32KKqgVxDnq2RKAQLxozfoasnRg");
        String id = profile.getId();
        System.out.println(id);
    }

    @Test
    void createPlaylist() {
        SpotifyPlaylist playlist = client.createPlaylist("31smzwkd4b4zk5lq74buj6n6lpf4", "BQAB633pyF-Ve5udTGfQ08YCCEr0KhrszgGBLHVwEHmtiDBp0lKOEuywec4t2eN6J8Dah8tu-4dEc557R3Mxgs3AvX0guFgWjoOFNesEFQ2eRNowoZC52H1-oq8DfVTS2auPNKK588OSi-wtQaH1dk12XFflj7My9XJ7cO6OQzqThg8QrTJ7iIq_ydTXjRXKE7HkIdsgPoCNy3AY4WwtMe7QuyyI_VKfQcwYwgvd_v7gP9LvQzzLcp69PeaCnUUVfKdNVvfAuyf-eckJwrpfErCadPnMjE3VAw", "test-playlist", "test-description", true);
        String id = playlist.getId();
        System.out.println(id);
    }

    @Test
    void addTracksToPlaylist() {
        List<String> songIds = List.of("03rcC8SVxCa9UdY5qgkITe", "08dz3ygXyFur6bL7Au8u8J");
        SpotifyPlaylistAddTracksRes playlist = client.addTracksToPlaylist("4rzhfMykEf6LZNFtwSS7JH", "BQAB633pyF-Ve5udTGfQ08YCCEr0KhrszgGBLHVwEHmtiDBp0lKOEuywec4t2eN6J8Dah8tu-4dEc557R3Mxgs3AvX0guFgWjoOFNesEFQ2eRNowoZC52H1-oq8DfVTS2auPNKK588OSi-wtQaH1dk12XFflj7My9XJ7cO6OQzqThg8QrTJ7iIq_ydTXjRXKE7HkIdsgPoCNy3AY4WwtMe7QuyyI_VKfQcwYwgvd_v7gP9LvQzzLcp69PeaCnUUVfKdNVvfAuyf-eckJwrpfErCadPnMjE3VAw", songIds,0);
        String snapshotId = playlist.getSnapshotId();
        System.out.println(snapshotId);
    }

    @Test
    void getRecommendations() {
        RecommendationsDto recommendations = client.getRecommendations(List.of("03rcC8SVxCa9UdY5qgkITe", "08dz3ygXyFur6bL7Au8u8J", "1BpKJw4RZxaFB88NE5uxXf", "1e1JKLEDKP7hEQzJfNAgPl", "1eMNW1HQjF1dbb4GtnmpaX"), "BQD7caoiVaynBDiQvErI-VCT2kFSkKPy4JsyiqEQhHouT7Lp7E5ttUZUcbq0qw-pC72X4LVAe4G-0ZSgdyDAUTOQYHtKLxpPEDrIMIDJQFs7MkEa4vy9u13LyvKiM5Mfo_lKjhF0Pj1n_K5pJ8tni504ZKdliynGxfKoaSD1YK9v_Lcvwqv074CGZJnUftRIdhDoA-3QBr3wsg1E0dkBW9ucLzWZtEuSd2cpcN6GZHoNnJJ94mfrYRHpfLmHN6kfnzYYSPCym3TN3gazGVBRDK9inLwLMxIIIw");
        recommendations.getTracks().forEach(t -> {
            System.out.println("id: " + t.getId());
            System.out.println("name: " + t.getName());
            System.out.println("uri: " + t.getUri());
            System.out.println("type: " + t.getType());
            System.out.println("artists: " + t.getArtists());
            System.out.println("images: " + t.getAlbum().getImages());
            System.out.println();
        });
    }
}