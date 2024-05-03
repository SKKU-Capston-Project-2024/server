package site.mutopia.server.spotify.dto.item;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Item{
    public String album_type;
    public int total_tracks;
    public ArrayList<String> available_markets;
    public ExternalUrls external_urls;
    public String href;
    public String id;
    public ArrayList<Image> images;
    public String name;
    public String release_date;
    public String release_date_precision;
    public Restrictions restrictions;
    public String type;
    public String uri;
    public ArrayList<Artist> artists;
}
