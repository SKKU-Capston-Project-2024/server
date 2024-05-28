package site.mutopia.server.spotify.dto.playlist;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class SpotifyPlaylistAddTracksReq {

    @JsonProperty("uris")
    private List<String> uris;

    @JsonProperty("position")
    private Integer position;

}