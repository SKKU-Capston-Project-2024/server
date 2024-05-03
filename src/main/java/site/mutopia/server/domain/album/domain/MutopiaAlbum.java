package site.mutopia.server.domain.album.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@Entity
@Table(name = "mutopia_album")
@AllArgsConstructor
public class MutopiaAlbum {

    @Id
    String id;

    @Column
    String name;

    @Column
    String artistName;

    @Column
    String coverImageUrl;

    @Column
    String releaseDate;

    @Column
    Long length;

    public MutopiaAlbum() {

    }

}