package site.mutopia.server.domain.playlist.entity;

import jakarta.persistence.*;
import lombok.*;
import site.mutopia.server.domain.user.entity.UserEntity;

@Entity
@Table(name = "playlist")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class PlaylistEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private UserEntity creator;
}