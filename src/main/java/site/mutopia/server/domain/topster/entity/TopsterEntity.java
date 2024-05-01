package site.mutopia.server.domain.topster.entity;

import jakarta.persistence.*;
import site.mutopia.server.domain.user.entity.UserEntity;

@Entity
@Table(name = "topster")
public class TopsterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "topster_id")
    private Long topsterId;

    @Column(name = "title")
    private String title;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private UserEntity user;
}
