package site.mutopia.server.domain.user.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "username")
    private String username;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "provider")
    private String provider;

    @Column(name = "provider_id")
    private String providerId;

    // TODO: Use Spring Data JPA Auditing (createdAt, updatedAt)
    // @CreationTimestamp
    // @Column(name = "created_at")
    // private LocalDateTime createdAt;
}
