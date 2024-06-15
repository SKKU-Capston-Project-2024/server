package site.mutopia.server.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import site.mutopia.server.domain.profile.repository.ProfileRepository;
import site.mutopia.server.domain.user.entity.UserEntity;
import site.mutopia.server.domain.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    @Value("${kakao.admin-key}")
    private String kakaoAdminKey;

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;

    @Transactional
    public void withdrawUser(UserEntity user) {
        user.setEmail(null);

        if (user.getProvider().equals("kakao")) {
            unlinkUserFromKakao(user.getProviderId());
        }
        user.getProfile().setBiography(null);
        user.getProfile().setProfilePicUrl(null);

        user.setUsername("알수없음");
        user.setProvider(null);
        user.setProviderId(null);
        profileRepository.save(user.getProfile());
        userRepository.save(user);
    }

    public void unlinkUserFromKakao(String userId) {
        String url = "https://kapi.kakao.com/v1/user/unlink";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + kakaoAdminKey);

        //x-www-form-urlencoded
        headers.set("Content-Type", "application/x-www-form-urlencoded");

        String content = "target_id_type=user_id&target_id=" + userId;

        HttpEntity<String> entity = new HttpEntity<>(content, headers);

        restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

    }

}
