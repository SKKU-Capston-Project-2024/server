package site.mutopia.server.domain.auth;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;

public enum OAuthAttributes {
    GOOGLE("google", (attributes) -> {
        return AuthUserInfo.builder()
                .provider("google")
                .providerId((String) attributes.get("sub"))
                .build();
    }),
    KAKAO("kakao", (attributes) -> {
        return AuthUserInfo.builder()
                .provider("kakao")
                .providerId(attributes.get("id").toString())
                .build();
    });

    private final String registrationId;
    private final Function<Map<String, Object>, AuthUserInfo> of;

    OAuthAttributes(String registrationId, Function<Map<String, Object>, AuthUserInfo> of) {
        this.registrationId = registrationId;
        this.of = of;
    }

    public static AuthUserInfo extract(String registrationId, Map<String, Object> attributes) {
        return Arrays.stream(values())
                .filter(oAuthAttributes -> oAuthAttributes.registrationId.equals(registrationId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unsupported registrationId: " + registrationId))
                .of.apply(attributes);
    }
}
