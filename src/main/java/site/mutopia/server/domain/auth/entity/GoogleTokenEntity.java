package site.mutopia.server.domain.auth.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.mutopia.server.domain.user.entity.UserEntity;

import java.time.Instant;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "google_token")
@IdClass(GoogleTokenId.class)
public class GoogleTokenEntity {

    @Id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Id
    @Column(name = "token_type")
    @Enumerated(EnumType.STRING)
    private GoogleTokenType tokenType;

    @Column(name = "token_value")
    private String tokenValue;

    @Column(name = "issued_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date issuedAt;

    @Column(name = "expires_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expiresAt;


    @Builder
    public GoogleTokenEntity(GoogleTokenType tokenType, UserEntity user, String tokenValue, Instant issuedAt, Instant expiresAt) {
        this.tokenType = tokenType;
        this.user = user;
        this.tokenValue = tokenValue;
        this.issuedAt = Date.from(issuedAt);
        this.expiresAt = expiresAt != null ? Date.from(expiresAt) : null;
    }
}
