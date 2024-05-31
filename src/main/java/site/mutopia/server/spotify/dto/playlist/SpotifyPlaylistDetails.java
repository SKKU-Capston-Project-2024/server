package site.mutopia.server.spotify.dto.playlist;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.mutopia.server.domain.playlist.dto.PlaylistTrendingResDto;
import site.mutopia.server.domain.playlist.dto.PlaylistTrendingResDto.Song;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SpotifyPlaylistDetails {

    @JsonIgnore
    private boolean collaborative;

    @JsonProperty("description")
    private String description;

    @JsonIgnore
    private ExternalUrls externalUrls;

    @JsonIgnore
    private Followers followers;

    @JsonIgnore
    private String href;

    @JsonProperty("id")
    private String id;

    @JsonProperty("images")
    private List<Image> images;

    @JsonProperty("name")
    private String name;

    @JsonIgnore
    private Owner owner;

    @JsonIgnore
    @JsonProperty("public")
    private boolean isPublic;

    @JsonIgnore
    private String snapshotId;

    @JsonProperty("tracks")
    private Tracks tracks;

    @JsonProperty("type")
    private String type;

    @JsonProperty("uri")
    private String uri;

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ExternalUrls {
        @JsonIgnore
        private String spotify;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Followers {
        @JsonIgnore
        private String href;

        @JsonIgnore
        private int total;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Image {
        @JsonProperty("url")
        private String url;

        @JsonProperty("height")
        private int height;

        @JsonProperty("width")
        private int width;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Owner {
        @JsonIgnore
        private ExternalUrls externalUrls;

        @JsonIgnore
        private Followers followers;

        @JsonIgnore
        private String href;

        @JsonIgnore
        private String id;

        @JsonIgnore
        private String type;

        @JsonIgnore
        private String uri;

        @JsonIgnore
        private String displayName;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Tracks {
        @JsonIgnore
        private String href;

        @JsonIgnore
        private int limit;

        @JsonIgnore
        private String next;

        @JsonIgnore
        private int offset;

        @JsonIgnore
        private String previous;

        @JsonIgnore
        private int total;

        @JsonProperty("items")
        private List<PlaylistTrackObject> items;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class PlaylistTrackObject {
        @JsonIgnore
        private String addedAt;

        @JsonIgnore
        private AddedBy addedBy;

        @JsonIgnore
        private boolean local;

        @JsonProperty("track")
        private Track track;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Track {
        @JsonProperty("id")
        private String id;

        @JsonProperty("name")
        private String name;

        @JsonProperty("album")
        private Album album;

        @JsonProperty("artists")
        private List<Artist> artists;

        @JsonIgnore
        private List<String> availableMarkets;

        @JsonIgnore
        private int discNumber;

        @JsonProperty("duration_ms")
        private int durationMs;

        @JsonIgnore
        private boolean explicit;

        @JsonIgnore
        private ExternalIds externalIds;

        @JsonIgnore
        private ExternalUrls externalUrls;

        @JsonIgnore
        private String href;

        @JsonIgnore
        private boolean playable;

        @JsonIgnore
        private int popularity;

        @JsonIgnore
        private String previewUrl;

        @JsonProperty("track_number")
        private int trackNumber;

        @JsonProperty("type")
        private String type;

        @JsonIgnore
        private String uri;

        @JsonIgnore
        private boolean local;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Album {
        @JsonProperty("artists")
        private List<Artist> artists;

        @JsonIgnore
        private String albumType;

        @JsonIgnore
        private int totalTracks;

        @JsonIgnore
        private List<String> availableMarkets;

        @JsonIgnore
        private ExternalUrls externalUrls;

        @JsonIgnore
        private String href;

        @JsonProperty("id")
        private String id;

        @JsonProperty("images")
        private List<Image> images;

        @JsonProperty("name")
        private String name;

        @JsonIgnore
        private String releaseDate;

        @JsonIgnore
        private String releaseDatePrecision;

        @JsonIgnore
        private Restrictions restrictions;

        @JsonIgnore
        private String type;

        @JsonIgnore
        private String uri;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Artist {
        @JsonIgnore
        private ExternalUrls externalUrls;

        @JsonIgnore
        private String href;

        @JsonProperty("id")
        private String id;

        @JsonProperty("name")
        private String name;

        @JsonProperty("type")
        private String type;

        @JsonIgnore
        private String uri;

        @JsonIgnore
        private Followers followers;

        @JsonIgnore
        private List<String> genres;

        @JsonIgnore
        private List<Image> images;

        @JsonIgnore
        private int popularity;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ExternalIds {
        @JsonIgnore
        private String isrc;

        @JsonIgnore
        private String ean;

        @JsonIgnore
        private String upc;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Restrictions {
        @JsonIgnore
        private String reason;
    }

    public PlaylistTrendingResDto toDto() {
        return PlaylistTrendingResDto.builder()
                .songs(this.tracks.items.stream().map(trackItem -> Song.builder()
                        .id(trackItem.getTrack().getId())
                        .name(trackItem.getTrack().getName())
                        .image(!trackItem.getTrack().getAlbum().getImages().isEmpty() ? PlaylistTrendingResDto.Image.builder()
                                .url(trackItem.getTrack().getAlbum().getImages().get(0).getUrl())
                                .height(trackItem.getTrack().getAlbum().getImages().get(0).getHeight())
                                .width(trackItem.getTrack().getAlbum().getImages().get(0).getWidth())
                                .build() : null)
                        .artists(trackItem.getTrack().getAlbum().getArtists().stream().map(artist -> PlaylistTrendingResDto.Artist.builder().id(artist.getId()).name(artist.getName()).build()).toList())
                        .build()).toList())
                .build();
    }
}
