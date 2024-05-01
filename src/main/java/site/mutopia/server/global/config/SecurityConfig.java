package site.mutopia.server.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import site.mutopia.server.domain.auth.AuthService;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthService authService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable);

        http.cors(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests((auth) ->
                auth.anyRequest().permitAll()
        );
        http.oauth2Login(new Customizer<OAuth2LoginConfigurer<HttpSecurity>>() {
            @Override
            public void customize(OAuth2LoginConfigurer<HttpSecurity> configurer) {
                configurer.defaultSuccessUrl("/oauth/loginInfo",true);
                configurer.userInfoEndpoint(new Customizer<OAuth2LoginConfigurer<HttpSecurity>.UserInfoEndpointConfig>() {
                    @Override
                    public void customize(OAuth2LoginConfigurer.UserInfoEndpointConfig userInfoEndpointConfig) {
                        userInfoEndpointConfig.userService(authService);

                    }
                });
            }
        });


        return http.build();
    }
}
