package site.mutopia.server.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import site.mutopia.server.domain.auth.annotation.LoginUser;
import site.mutopia.server.domain.user.entity.UserEntity;
import site.mutopia.server.swagger.authorization.DisableSwaggerAuth;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Stream;

@Slf4j
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
                .addServersItem(new io.swagger.v3.oas.models.servers.Server().url("http://localhost:8080"))
                .addServersItem(new io.swagger.v3.oas.models.servers.Server().url("https://mutopia.site"))
                .components(components);
    }

    private Info apiInfo() {
        return new Info()
                .title("Mutopia API")
                .description("Mutopia API")
                .version("1.0");
    }

    @Bean
    public OperationCustomizer customizer(){
        return (operation, handlerMethod) -> {
            Stream<MethodParameter> params = Arrays.stream(handlerMethod.getMethodParameters());
            MethodParameter loginUserParam = params.filter(methodParameter -> methodParameter.hasParameterAnnotation(LoginUser.class)).findFirst().orElse(null);
            if(loginUserParam != null){
                String loginUserParamName = loginUserParam.getParameter().getName();

                log.info("loginUserSchemaName: {}", loginUserParamName);
                operation.getParameters().removeIf(parameter -> parameter.getName().equals(loginUserParamName));

                ApiResponses responses = operation.getResponses();
                ApiResponse unauthorized = new ApiResponse().description("JWT 토큰 인증 실패");
                responses.addApiResponse("401", unauthorized);
            }
            else{
                operation.setSecurity(Collections.emptyList());
            }
            return operation;
        };
    }
}
