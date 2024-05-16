package site.mutopia.server.domain.topster.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.mutopia.server.domain.user.entity.UserEntity;

@Entity
@Table(name = "topster")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TopsterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "topster_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Builder
    public TopsterEntity(UserEntity user) {
        this.user = user;
    }
}
