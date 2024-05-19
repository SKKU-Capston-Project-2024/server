package site.mutopia.server.domain.follow.dto;

import lombok.Data;

@Data
public class FollowerInfoDto {
    private String userId;
    private String profileImageUrl;
    private String nickname;

    public FollowerInfoDto(String userId, String profileImageUrl, String nickname) {
        this.userId = userId;
        this.profileImageUrl = profileImageUrl;
        this.nickname = nickname;
    }
}
