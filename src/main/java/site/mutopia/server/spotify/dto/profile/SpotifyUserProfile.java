package site.mutopia.server.spotify.dto.profile;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SpotifyUserProfile {

    @JsonProperty("country")
    private String country;

    @JsonProperty("display_name")
    private String displayName;

    @JsonProperty("email")
    private String email;

    @JsonProperty("explicit_content")
    private ExplicitContent explicitContent;

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

    @JsonProperty("product")
    private String product;

    @JsonProperty("type")
    private String type;

    @JsonProperty("uri")
    private String uri;


    public static class ExplicitContent {
        @JsonProperty("filter_enabled")
        private boolean filterEnabled;

        @JsonProperty("filter_locked")
        private boolean filterLocked;
    }

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
}
