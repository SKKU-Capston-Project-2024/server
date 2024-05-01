package site.mutopia.server.domain.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import site.mutopia.server.domain.user.entity.UserEntity;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class AuthService implements OAuth2UserService<OAuth2UserRequest,OAuth2User> {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User authUser = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        Map<String, Object> attributes = authUser.getAttributes();

        // log every attribute
        log.info("attributes: {}", attributes.entrySet().stream().map(e -> e.getKey() + " : " + e.getValue()).reduce("", (a, b) -> a + b + "\n"));

        AuthUserInfo userInfo = OAuthAttributes.extract(registrationId, attributes);
        userInfo.setProvider(registrationId);

        UserEntity user = saveOrUpdate(userInfo);

        Map<String,Object> customAttributes = customAttributes(userInfo);

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("USER")),
                customAttributes,
                userNameAttributeName
        );

    }

    private Map<String,Object> customAttributes(AuthUserInfo userInfo) {
        return Map.of("provider", userInfo.getProvider(),
                "sub", userInfo.getProviderId(),
                "email", userInfo.getEmail()
        );
    }

    private UserEntity saveOrUpdate(AuthUserInfo userInfo) {
        UserEntity user = new UserEntity();
        user.setEmail(userInfo.getEmail());
        user.setProvider(userInfo.getProvider());
        user.setProviderId(userInfo.getProviderId());
        user.setUsername(userInfo.getUsername());

        //userRepository.save(user);
        return user;
    }
}
