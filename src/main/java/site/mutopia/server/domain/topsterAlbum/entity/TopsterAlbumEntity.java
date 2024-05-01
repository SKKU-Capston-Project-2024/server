package site.mutopia.server.domain.topsterAlbum.entity;

import jakarta.persistence.*;
import site.mutopia.server.domain.album.entity.AlbumEntity;
import site.mutopia.server.domain.topster.entity.TopsterEntity;

@Entity
@Table(name = "topster_album")
@IdClass(TopsterAlbumId.class)
public class TopsterAlbumEntity {
    @Id
    @ManyToOne
    @JoinColumn(name = "topster_id")
    private TopsterEntity topster;

    @Id
    @ManyToOne
    @JoinColumn(name = "album_id")
    private AlbumEntity album;
}