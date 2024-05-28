package site.mutopia.server.spotify.dto.playlist;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SpotifyPlaylist {

    @JsonProperty("collaborative")
    private boolean collaborative;

    @JsonProperty("description")
    private String description;

    @JsonProperty("external_urls")
    private ExternalUrls externalUrls;

    @JsonProperty("followers")
    private Followers followers;

    @JsonProperty("href")
    private String href;

    @JsonProperty("id")
    private String id;

    @JsonProperty("images")
    private List<Image> images;

    @JsonProperty("name")
    private String name;

    @JsonProperty("owner")
    private Owner owner;

    @JsonProperty("public")
    private boolean isPublic;

    @JsonProperty("snapshot_id")
    private String snapshotId;

    @JsonProperty("tracks")
    private Tracks tracks;

    @JsonProperty("type")
    private String type;

    @JsonProperty("uri")
    private String uri;

    public static class ExternalUrls {
        @JsonProperty("spotify")
        private String spotify;
    }

    public static class Followers {
        @JsonProperty("href")
        private String href;

        @JsonProperty("total")
        private int total;
    }

    public static class Image {
        @JsonProperty("url")
        private String url;

        @JsonProperty("height")
        private int height;

        @JsonProperty("width")
        private int width;
    }

    public static class Owner {
        @JsonProperty("external_urls")
        private ExternalUrls externalUrls;

        @JsonProperty("followers")
        private Followers followers;

        @JsonProperty("href")
        private String href;

        @JsonProperty("id")
        private String id;

        @JsonProperty("type")
        private String type;

        @JsonProperty("uri")
        private String uri;

        @JsonProperty("display_name")
        private String displayName;
    }

    public static class Tracks {
        @JsonProperty("href")
        private String href;

        @JsonProperty("limit")
        private int limit;

        @JsonProperty("next")
        private String next;

        @JsonProperty("offset")
        private int offset;

        @JsonProperty("previous")
        private String previous;

        @JsonProperty("total")
        private int total;

        @JsonProperty("items")
        private List<TrackItem> items;
    }

    public static class TrackItem {
        @JsonProperty("added_at")
        private String addedAt;

        @JsonProperty("added_by")
        private AddedBy addedBy;

        @JsonProperty("is_local")
        private boolean isLocal;

        @JsonProperty("track")
        private Track track;
    }

    public static class AddedBy {
        @JsonProperty("external_urls")
        private ExternalUrls externalUrls;

        @JsonProperty("followers")
        private Followers followers;

        @JsonProperty("href")
        private String href;

        @JsonProperty("id")
        private String id;

        @JsonProperty("type")
        private String type;

        @JsonProperty("uri")
        private String uri;
    }

    public static class Track {
        @JsonProperty("album")
        private Album album;

        @JsonProperty("artists")
        private List<Artist> artists;

        @JsonProperty("available_markets")
        private List<String> availableMarkets;

        @JsonProperty("disc_number")
        private int discNumber;

        @JsonProperty("duration_ms")
        private int durationMs;

        @JsonProperty("explicit")
        private boolean explicit;

        @JsonProperty("external_ids")
        private ExternalIds externalIds;

        @JsonProperty("external_urls")
        private ExternalUrls externalUrls;

        @JsonProperty("href")
        private String href;

        @JsonProperty("id")
        private String id;

        @JsonProperty("is_playable")
        private boolean isPlayable;

        @JsonProperty("name")
        private String name;

        @JsonProperty("popularity")
        private int popularity;

        @JsonProperty("preview_url")
        private String previewUrl;

        @JsonProperty("track_number")
        private int trackNumber;

        @JsonProperty("type")
        private String type;

        @JsonProperty("uri")
        private String uri;

        @JsonProperty("is_local")
        private boolean isLocal;
    }

    public static class Album {
        @JsonProperty("album_type")
        private String albumType;

        @JsonProperty("total_tracks")
        private int totalTracks;

        @JsonProperty("available_markets")
        private List<String> availableMarkets;

        @JsonProperty("external_urls")
        private ExternalUrls externalUrls;

        @JsonProperty("href")
        private String href;

        @JsonProperty("id")
        private String id;

        @JsonProperty("images")
        private List<Image> images;

        @JsonProperty("name")
        private String name;

        @JsonProperty("release_date")
        private String releaseDate;

        @JsonProperty("release_date_precision")
        private String releaseDatePrecision;

        @JsonProperty("restrictions")
        private Restrictions restrictions;

        @JsonProperty("type")
        private String type;

        @JsonProperty("uri")
        private String uri;

        @JsonProperty("artists")
        private List<Artist> artists;
    }

    public static class Artist {
        @JsonProperty("external_urls")
        private ExternalUrls externalUrls;

        @JsonProperty("href")
        private String href;

        @JsonProperty("id")
        private String id;

        @JsonProperty("name")
        private String name;

        @JsonProperty("type")
        private String type;

        @JsonProperty("uri")
        private String uri;

        @JsonProperty("followers")
        private Followers followers;

        @JsonProperty("genres")
        private List<String> genres;

        @JsonProperty("images")
        private List<Image> images;

        @JsonProperty("popularity")
        private int popularity;
    }

    public static class ExternalIds {
        @JsonProperty("isrc")
        private String isrc;

        @JsonProperty("ean")
        private String ean;

        @JsonProperty("upc")
        private String upc;
    }

    public static class Restrictions {
        @JsonProperty("reason")
        private String reason;
    }
}
