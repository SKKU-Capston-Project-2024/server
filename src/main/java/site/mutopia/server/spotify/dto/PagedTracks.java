package site.mutopia.server.spotify.dto;

import lombok.Data;
import site.mutopia.server.spotify.dto.item.Track;

import java.util.List;

@Data
public class PagedTracks {
    private String href;
    private int limit;
    private String next;
    private int offset;
    private String previous;
    private int total;
    private List<Track> items;
    // getters and setters
}

