package site.mutopia.server.spotify.dto.playlist;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SpotifyPlaylistAddTracksRes {

    @JsonProperty("snapshot_id")
    private String snapshotId;

}