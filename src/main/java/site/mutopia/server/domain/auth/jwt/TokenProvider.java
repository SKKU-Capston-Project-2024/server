package site.mutopia.server.domain.auth.jwt;

import com.nimbusds.oauth2.sdk.auth.Secret;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.token.Token;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import site.mutopia.server.domain.auth.exception.UnAuthorizedException;
import site.mutopia.server.domain.user.entity.UserEntity;
import site.mutopia.server.domain.user.repository.UserRepository;

import javax.crypto.SecretKey;
import javax.swing.text.html.Option;
import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TokenProvider {

    @Value("${jwt.key}")
    private String key;

    private SecretKey secretKey;
    private final UserRepository userRepository;


    private static final String KEY_ROLE = "role";
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7L;
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 30L;

    public String generateAccessToken(Authentication auth) {
        return generateToken(auth, ACCESS_TOKEN_EXPIRE_TIME);
    }

    public String generateRefreshToken(Authentication auth) {
        return generateToken(auth, REFRESH_TOKEN_EXPIRE_TIME);
    }

    @PostConstruct
    protected void init() {
        secretKey = Keys.hmacShaKeyFor(key.getBytes());
    }


    public String generateToken(Authentication auth, long expireTime) {
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + expireTime);

        String authorities = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining());

        return Jwts.builder()
                .setSubject(auth.getName())
                .claim(KEY_ROLE, authorities)
                .setIssuedAt(now)
                .setExpiration(expireDate)
                .signWith(secretKey)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = parseClaim(token);
        List<SimpleGrantedAuthority> authorities = getAuthorities(claims);

        User principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public String reIssueToken(String refreshToken) {
        Claims claims = parseClaim(refreshToken);
        return generateAccessToken(getAuthentication(refreshToken));
    }

    private List<SimpleGrantedAuthority> getAuthorities(Claims claims) {
        return Collections.singletonList(new SimpleGrantedAuthority(claims.get(KEY_ROLE).toString()));
    }

    private Claims parseClaim(String token) {
        try{
            return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
        }catch (ExpiredJwtException e){
            return e.getClaims();
        }catch (MalformedParameterizedTypeException e){
            throw new IllegalArgumentException("Invalid Token");
        }
        catch (SecurityException e) {
            throw new IllegalArgumentException("Invalid jwt signature");
        }
    }

    public Optional<UserEntity> getUserEntity(String token) {
        try{
            if(token == null || StringUtils.isEmpty(token)){
                return Optional.empty();
            }
            Claims claims = parseClaim(token);
            return userRepository.findById(claims.getSubject());}
        catch (Exception e){
            return Optional.empty();
        }
    }


}
