package site.mutopia.server.domain.follow.dto;

import lombok.Data;

@Data
public class FollowerInfoDto {
    private String userId;
    private String profileImageUrl;
    private String nickname;
    private boolean isFollowing;

    public FollowerInfoDto(String userId, String profileImageUrl, String nickname, boolean isFollowing) {
        this.userId = userId;
        this.profileImageUrl = profileImageUrl;
        this.nickname = nickname;
        this.isFollowing = isFollowing;
    }
}
