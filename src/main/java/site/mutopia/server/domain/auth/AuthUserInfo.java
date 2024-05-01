package site.mutopia.server.domain.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import site.mutopia.server.domain.user.entity.UserEntity;

@Getter
@Setter
@Builder
public class AuthUserInfo {
    private String provider;
    private String providerId;
    private String userId;

}
