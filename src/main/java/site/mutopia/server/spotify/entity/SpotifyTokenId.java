package site.mutopia.server.spotify.entity;

import lombok.EqualsAndHashCode;
import java.io.Serializable;

@EqualsAndHashCode
public class SpotifyTokenId implements Serializable {
    private String user;
    private SpotifyTokenType tokenType;
}