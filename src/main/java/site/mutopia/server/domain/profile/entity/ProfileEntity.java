package site.mutopia.server.domain.profile.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import site.mutopia.server.domain.user.entity.UserEntity;

@Getter
@Setter
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

    @Column(name = "biography")
    private String biography;

    @Column(name = "profile_pic_url")
    private String profilePicUrl;

    public void modifyBio(String bio) {
        this.biography = bio;
    }

    public void modifyProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
    }


    public static ProfileEntity newUserProfile(UserEntity userEntity, String profilePicUrl) {
        ProfileEntity profileEntity = new ProfileEntity();
        profileEntity.profilePicUrl = profilePicUrl;
        profileEntity.user = userEntity;
        profileEntity.biography = "";
        return profileEntity;
    }
}