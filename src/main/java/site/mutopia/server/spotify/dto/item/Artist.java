package site.mutopia.server.spotify.dto.item;

import lombok.Data;

@Data
public class Artist{
    public ExternalUrls external_urls;
    public String href;
    public String id;
    public String name;
    public String type;
    public String uri;
}
