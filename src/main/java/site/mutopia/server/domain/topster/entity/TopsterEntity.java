package site.mutopia.server.domain.topster.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import site.mutopia.server.domain.user.entity.UserEntity;
import java.time.LocalDateTime;

@Entity
@Table(name = "topster")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TopsterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "topster_id")
    private Long id;

    @Column(name = "title")
    private String title;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Builder
    public TopsterEntity(String title, UserEntity user) {
        this.title = title;
        this.user = user;
    }
}
