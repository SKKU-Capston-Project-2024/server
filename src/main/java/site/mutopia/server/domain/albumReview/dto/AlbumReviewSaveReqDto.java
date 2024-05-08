package site.mutopia.server.domain.albumReview.dto;

import lombok.*;
import site.mutopia.server.domain.album.entity.AlbumEntity;
import site.mutopia.server.domain.albumReview.entity.AlbumReviewEntity;
import site.mutopia.server.domain.user.entity.UserEntity;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AlbumReviewSaveReqDto {

    private String title;
    private String content;
    private Integer rating;
    private String albumId;

    public AlbumReviewEntity toEntity(UserEntity writer, AlbumEntity album) {
        return AlbumReviewEntity.builder()
                .title(this.getTitle())
                .content(this.getContent())
                .rating(this.getRating())
                .writer(writer)
                .album(album)
                .build();
    }
}
