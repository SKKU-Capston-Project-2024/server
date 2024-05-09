package site.mutopia.server.domain.topster.entity;

import jakarta.persistence.*;
import lombok.*;
import site.mutopia.server.domain.album.entity.AlbumEntity;

@Entity
@Table(name = "topster_album")
@IdClass(TopsterAlbumId.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class TopsterAlbumEntity {
    @Id
    @ManyToOne
    @JoinColumn(name = "topster_id")
    @MapsId("topster")
    private TopsterEntity topster;

    @Id
    @ManyToOne
    @JoinColumn(name = "album_id")
    @MapsId("album")
    private AlbumEntity album;
}