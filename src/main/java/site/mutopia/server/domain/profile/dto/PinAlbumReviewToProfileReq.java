package site.mutopia.server.domain.profile.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PinAlbumReviewToProfileReq {
    private Long albumReviewId;
}
