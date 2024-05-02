package site.mutopia.server.domain.profile.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import site.mutopia.server.domain.user.entity.UserEntity;

@Entity
@Setter
@Getter
@Table(name = "profile")
public class ProfileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id")
    private String profileId;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private UserEntity user;

    @Column(name = "bio")
    private String bio;

    @Column(name = "profile_pic_url")
    private String profilePicUrl;
}