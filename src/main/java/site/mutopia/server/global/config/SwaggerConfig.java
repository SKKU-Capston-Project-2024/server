package site.mutopia.server.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;

import java.io.IOException;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        String jwt = "JWT";
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwt);
        Components components = new Components()
                .addSecuritySchemes(jwt, new SecurityScheme()
                        .name(jwt)
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat(jwt));
        return new OpenAPI()
                .components(new Components()).info(apiInfo())
                .addSecurityItem(securityRequirement)
                .components(components);
    }

    private Info apiInfo() {
        return new Info()
                .title("Mutopia API")
                .description("Mutopia API")
                .version("1.0");
    }

    @Configuration
    public static class SpotifyConfig {

        @Value("${spotify.client.id}")
        private String clientId;

        @Value("${spotify.client.secret}")
        private String clientSecret;

        @Bean
        public SpotifyApi spotifyApi() throws IOException, ParseException, SpotifyWebApiException {
            SpotifyApi spotifyApi = new SpotifyApi.Builder()
                    .setClientId(clientId)
                    .setClientSecret(clientSecret)
                    .build();
            ClientCredentials credentials = spotifyApi.clientCredentials().build().execute();
            String accessToken = credentials.getAccessToken();
            return new SpotifyApi.Builder()
                    .setAccessToken(accessToken)
                    .build();
        }
    }
}
