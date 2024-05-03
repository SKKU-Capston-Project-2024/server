package site.mutopia.server.spotify.dto;

import lombok.Data;
import site.mutopia.server.spotify.dto.item.Albums;

@Data
public class SearchAlbumsDto{
    public Albums albums;
}
