package site.mutopia.server.domain.album.repository;


import site.mutopia.server.domain.album.entity.AlbumEntity;

import java.util.List;
import java.util.Optional;

public interface AlbumRepository {

    Optional<AlbumEntity> findAlbumById(String albumId);

    List<AlbumEntity> findAlbumByAlbumName(String albumName);

    List<AlbumEntity> findAlbumByArtistName(String artistName, int limit, int offset);

    List<AlbumEntity> findAlbumsByArtistNameOrAlbumName(String keyword);

    List<AlbumEntity> findAlbumByKeyword(String keyword, int limit, int offset);

    Long countLikesByAlbumId(String albumId);
}
