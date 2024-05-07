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
                singletonMap("userId", user.getId()),
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
        newUser.setUsername(randomUsername());

        return userRepository.save(newUser);
    }

    private String randomUsername() {
        String randomInt = String.valueOf(new Random().nextInt(89) + 11);
        return adjectives.get(new Random().nextInt(adjectives.size())) + "뮤토피아" + randomInt;
    }

    private static final List<String> adjectives = Arrays.asList(
            "멋진", "예쁜", "행복한", "슬픈", "즐거운", "밝은", "어두운", "힘든", "쉬운", "빠른",
            "느린", "작은", "맛있는", "신선한", "재미있는", "지루한", "시끄러운", "조용한", "착한",
            "나쁜", "더운", "추운", "강한", "약한", "높은", "낮은", "짧은", "굵은",
            "가는", "부드러운", "딱딱한", "부유한", "가난한", "젊은", "노인", "아름다운",
            "푸른", "붉은", "노란", "하얀", "검은", "서운한", "중요한", "다른", "비슷한", "관련있는",
            "필요한", "필수적인", "기쁜", "유쾌한", "흥미로운", "거대한", "소중한", "짜릿한", "멋있는", "무서운",
            "안타까운", "우아한", "고급스러운", "현대적인", "전통적인", "창의적인", "도전적인", "엄격한", "유연한", "단단한",
            "섬세한", "견고한", "불편한", "편안한", "잔잔한", "평온한", "화려한", "심플한", "복잡한", "명확한",
            "애매한", "강력한", "유명한", "미지의", "따뜻한", "시원한", "무거운", "가벼운",
            "날카로운", "넓은", "좁은", "놀라운", "좋은", "준비된", "용감한", "겁나는"
    );
}
