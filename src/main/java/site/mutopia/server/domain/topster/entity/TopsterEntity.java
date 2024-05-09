package site.mutopia.server.domain.topster.entity;

import jakarta.persistence.*;
import lombok.*;
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

    @Column(name = "title")
    private String title;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Builder
    public TopsterEntity(String title, UserEntity user) {
        this.title = title;
        this.user = user;
    }
}
