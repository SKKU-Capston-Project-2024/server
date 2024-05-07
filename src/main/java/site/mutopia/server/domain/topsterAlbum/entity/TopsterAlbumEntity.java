package site.mutopia.server.domain.topsterAlbum.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import site.mutopia.server.domain.album.entity.AlbumEntity;
import site.mutopia.server.domain.topster.entity.TopsterEntity;

@Entity
@Table(name = "topster_album")
@IdClass(TopsterAlbumId.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
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