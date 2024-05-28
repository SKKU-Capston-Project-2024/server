package site.mutopia.server.spotify.dto.login;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SpotifyLoginResDto {
    private String redirectUrl;
}
