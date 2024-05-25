package site.mutopia.server.spotify.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.mutopia.server.domain.user.entity.UserEntity;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@IdClass(SpotifyTokenId.class)
@Table(name = "spotify_token")
public class SpotifyTokenEntity {

    @Id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Id
    @Column(name = "token_type")
    @Enumerated(EnumType.STRING)
    private SpotifyTokenType tokenType;

    @Column(name = "token_value", length = 2048)
    private String tokenValue;

    @Column(name = "issued_at")
    private LocalDateTime issuedAt;

    @Builder
    public SpotifyTokenEntity(UserEntity user, SpotifyTokenType tokenType, String tokenValue, LocalDateTime issuedAt) {
        this.user = user;
        this.tokenType = tokenType;
        this.tokenValue = tokenValue;
        this.issuedAt = issuedAt;
    }
}