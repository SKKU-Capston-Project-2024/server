package site.mutopia.server.spotify.dto.item;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class Track {
    private List<Artist> artists;
    private List<String> available_markets;
    private int disc_number;
    private int duration_ms;
    private boolean explicit;
    private Map<String, String> external_urls;
    private String href;
    private String id;
    private boolean is_playable;
    private Track linked_from;
    private Restrictions restrictions;
    private String name;
    private String preview_url;
    private int track_number;
    private String type;
    private String uri;
    private boolean is_local;
}
