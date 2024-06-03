package site.mutopia.server.spotify.dto.trackinfo;

import java.util.ArrayList;

public class TrackInfo{
    public TrackInfoAlbum album;
    public ArrayList<Artist> artists;
    public int duration_ms;
    public String id;
    public String name;
    public String type;
    public String uri;
}
