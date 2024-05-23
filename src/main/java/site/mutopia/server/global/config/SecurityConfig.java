package site.mutopia.server.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import site.mutopia.server.domain.auth.AuthService;
import site.mutopia.server.domain.auth.AuthSuccessHandler;
import site.mutopia.server.domain.auth.CustomAuthorizationRequestResolver;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthService authService;

    private final AuthSuccessHandler authSuccessHandler;

    private final CustomAuthorizationRequestResolver customAuthorizationRequestResolver;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((auth) ->
                        auth.anyRequest().permitAll()
                )
                .logout(AbstractHttpConfigurer::disable)
                .sessionManagement((session) ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .oauth2Login(
                        (oauth2) -> oauth2.successHandler(authSuccessHandler)
                                .userInfoEndpoint(userInfo -> userInfo.userService(authService))
                                .authorizationEndpoint(endpoint -> endpoint.authorizationRequestResolver(customAuthorizationRequestResolver))
                );

        return http.build();
    }
}
