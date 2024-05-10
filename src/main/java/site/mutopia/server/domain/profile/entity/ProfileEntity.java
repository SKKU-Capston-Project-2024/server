package site.mutopia.server.domain.profile.entity;

import jakarta.persistence.*;
import lombok.Getter;
import site.mutopia.server.domain.user.entity.UserEntity;

@Getter
@Entity
@Table(name = "profile")
public class ProfileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id")
    private Long profileId;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private UserEntity user;

    @Column(name = "bio")
    private String bio;

    @Column(name = "profile_pic_url")
    private String profilePicUrl;

    public void modifyBio(String bio) {
        this.bio = bio;
    }

    public void modifyProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
    }


    public static ProfileEntity newUserProfile(UserEntity userEntity, String profilePicUrl) {
        ProfileEntity profileEntity = new ProfileEntity();
        profileEntity.profilePicUrl = profilePicUrl;
        profileEntity.user = userEntity;
        profileEntity.bio = "";
        return profileEntity;
    }
}