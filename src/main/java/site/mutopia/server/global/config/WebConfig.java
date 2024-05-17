package site.mutopia.server.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import site.mutopia.server.domain.auth.jwt.TokenProvider;
import site.mutopia.server.domain.auth.annotation.resolver.LoginUserResolver;

import java.util.List;

@Configuration
@EnableJpaAuditing
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {


    private final TokenProvider tokenProvider;


    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginUserResolver(tokenProvider));
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000", "https://mutopia-clone.vercel.app", "https://mutopia.site")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS");
    }
}
