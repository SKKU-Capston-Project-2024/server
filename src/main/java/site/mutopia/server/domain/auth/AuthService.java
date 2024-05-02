package site.mutopia.server.domain.auth;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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
import site.mutopia.server.domain.user.repository.UserRepository;

import java.util.*;

import static java.util.Collections.singletonMap;

@RequiredArgsConstructor
@Service
@Slf4j
public class AuthService implements OAuth2UserService<OAuth2UserRequest,OAuth2User> {

    private final UserRepository userRepository;

    @Override
    @Transactional
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

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("USER")),
                singletonMap("userId", user.getUserId()),
                "userId"
        );

    }

    private Map<String,Object> customAttributes(AuthUserInfo userInfo) {
        return Map.of("provider", userInfo.getProvider(),
                "sub", userInfo.getProviderId()

        );
    }

    protected UserEntity saveOrUpdate(AuthUserInfo userInfo) {

        log.info("provider:{} providerId:{}", userInfo.getProvider(), userInfo.getProviderId());
        Optional<UserEntity> user = userRepository.findByProviderAndProviderId(userInfo.getProvider(), userInfo.getProviderId());
        if (user.isPresent()) {
            return user.get();
        }
        UserEntity newUser = new UserEntity();
        newUser.setProvider(userInfo.getProvider());
        newUser.setProviderId(userInfo.getProviderId());

        return userRepository.save(newUser);
    }
}
