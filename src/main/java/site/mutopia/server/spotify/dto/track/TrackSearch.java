package site.mutopia.server.spotify.dto.track;

import java.util.ArrayList;

public class TrackSearch {
    public TrackAlbumInfo album;
    public ArrayList<Artist> artists;
    public int disc_number;
    public int duration_ms;
    public boolean explicit;
    public ExternalIds external_ids;
    public ExternalUrls external_urls;
    public String href;
    public String id;
    public boolean is_local;
    public boolean is_playable;
    public String name;
    public int popularity;
    public String preview_url;
    public int track_number;
    public String type;
    public String uri;
}
