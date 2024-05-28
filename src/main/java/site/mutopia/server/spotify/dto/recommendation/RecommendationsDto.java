package site.mutopia.server.spotify.dto.recommendation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class RecommendationsDto {
    @JsonIgnore
    private List<RecommendationSeedDto> seeds;

    @JsonProperty("tracks")
    private List<TrackDto> tracks;

    @Data
    public static class RecommendationSeedDto {
        @JsonProperty("afterFilteringSize")
        private int afterFilteringSize;

        @JsonProperty("afterRelinkingSize")
        private int afterRelinkingSize;

        @JsonProperty("href")
        private String href;

        @JsonProperty("id")
        private String id;

        @JsonProperty("initialPoolSize")
        private int initialPoolSize;

        @JsonProperty("type")
        private String type;
    }

    @Data
    public static class TrackDto {
        @JsonProperty("album")
        private AlbumDto album;

        @JsonProperty("artists")
        private List<ArtistDto> artists;

        @JsonIgnore
        private List<String> availableMarkets;

        @JsonIgnore
        private int discNumber;

        @JsonProperty("duration_ms")
        private int durationMs;

        @JsonIgnore
        private boolean explicit;

        @JsonIgnore
        private ExternalIdDto externalIds;

        @JsonIgnore
        private ExternalUrlDto externalUrls;

        @JsonIgnore
        private String href;

        @JsonProperty("id")
        private String id;

        @JsonProperty("is_playable")
        private boolean isPlayable;

        @JsonIgnore
        private Object linkedFrom;

        @JsonIgnore
        private RestrictionsDto restrictions;

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

        @JsonIgnore
        private boolean isLocal;
    }

    @Data
    public static class AlbumDto {
        @JsonProperty("album_type")
        private String albumType;

        @JsonProperty("total_tracks")
        private int totalTracks;

        @JsonIgnore
        private List<String> availableMarkets;

        @JsonIgnore
        private ExternalUrlDto externalUrls;

        @JsonIgnore
        private String href;

        @JsonProperty("id")
        private String id;

        @JsonProperty("images")
        private List<ImageDto> images;

        @JsonProperty("name")
        private String name;

        @JsonProperty("release_date")
        private String releaseDate;

        @JsonProperty("release_date_precision")
        private String releaseDatePrecision;

        @JsonIgnore
        private RestrictionsDto restrictions;

        @JsonProperty("type")
        private String type;

        @JsonProperty("uri")
        private String uri;

        @JsonProperty("artists")
        private List<ArtistDto> artists;
    }

    @Data
    public static class ArtistDto {
        @JsonIgnore
        private ExternalUrlDto externalUrls;

        @JsonProperty("followers")
        private FollowersDto followers;

        @JsonProperty("genres")
        private List<String> genres;

        @JsonIgnore
        private String href;

        @JsonProperty("id")
        private String id;

        @JsonProperty("images")
        private List<ImageDto> images;

        @JsonProperty("name")
        private String name;

        @JsonProperty("popularity")
        private int popularity;

        @JsonProperty("type")
        private String type;

        @JsonProperty("uri")
        private String uri;
    }

    @Data
    public static class ImageDto {
        @JsonProperty("url")
        private String url;

        @JsonProperty("height")
        private int height;

        @JsonProperty("width")
        private int width;
    }

    @Data
    public static class ExternalUrlDto {
        @JsonProperty("spotify")
        private String spotify;
    }

    @Data
    public static class ExternalIdDto {
        @JsonProperty("isrc")
        private String isrc;

        @JsonProperty("ean")
        private String ean;

        @JsonProperty("upc")
        private String upc;
    }

    @Data
    public static class RestrictionsDto {
        @JsonProperty("reason")
        private String reason;
    }

    @Data
    public static class FollowersDto {
        @JsonProperty("href")
        private String href;

        @JsonProperty("total")
        private int total;
    }
}
