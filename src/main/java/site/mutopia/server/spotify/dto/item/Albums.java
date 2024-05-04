package site.mutopia.server.spotify.dto.item;

import lombok.Data;

import java.util.ArrayList;


public class Albums{
    public String href;
    public int limit;
    public String next;
    public int offset;
    public String previous;
    public int total;
    public ArrayList<Item> items;
}
