package site.mutopia.server.domain.user.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import site.mutopia.server.domain.profile.entity.ProfileEntity;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id")
    private String id;

    @Column(name = "username")
    private String username;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "provider")
    private String provider;

    @Column(name = "provider_id")
    private String providerId;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private ProfileEntity profile;

    public void modifyUsername(String username) {
        this.username = username;
    }

    @Builder
    public UserEntity(String username, String email, String provider, String providerId) {
        this.username = username;
        this.email = email;
        this.provider = provider;
        this.providerId = providerId;
    }

    // TODO: Use Spring Data JPA Auditing (createdAt, updatedAt)
    // @CreationTimestamp
    // @Column(name = "created_at")
    // private LocalDateTime createdAt;
}
