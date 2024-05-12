package site.mutopia.server.domain.auth.annotation.resolver;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import site.mutopia.server.domain.auth.annotation.LoginUser;
import site.mutopia.server.domain.auth.exception.UnAuthorizedException;
import site.mutopia.server.domain.auth.jwt.TokenProvider;
import site.mutopia.server.domain.user.entity.UserEntity;

import java.util.Optional;


@Component
@RequiredArgsConstructor
@Slf4j
public class LoginUserResolver implements HandlerMethodArgumentResolver {

    private final TokenProvider tokenProvider;


    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        final String accessToken = AuthHeaderParser.parse((HttpServletRequest) webRequest.getNativeRequest());
        Optional<UserEntity> userEntity = tokenProvider.getUserEntity(accessToken);
        if (userEntity.isEmpty()) {
            log.debug("로그인");
            if(parameter.getParameterAnnotation(LoginUser.class).require()) {
                log.debug("로그인이 필요합니다.");
                throw new UnAuthorizedException("로그인이 필요합니다.");
            }
            else{
                return null;
            }
        }
        return userEntity.get();
    }
}
