package site.mutopia.server.spotify.dto.track;

import java.util.ArrayList;

public class Tracks{
    public String href;
    public ArrayList<TrackSearch> items;
    public int limit;
    public String next;
    public int offset;
    public Object previous;
    public int total;
}
