package site.mutopia.server.domain.topster.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "topster")
public class TopsterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "topster_id")
    private Long topsterId;

    @Column(name = "title")
    private String title;

    // TODO: Set relationships with other entities (User)
}
