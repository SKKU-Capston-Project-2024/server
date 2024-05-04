package site.mutopia.server.domain.album.repository;


import site.mutopia.server.domain.album.entity.AlbumEntity;

import java.util.List;

public interface AlbumRepository {

    AlbumEntity findAlbumById(String albumId);

    List<AlbumEntity> findAlbumByAlbumName(String albumName);

    List<AlbumEntity> findAlbumByArtistName(String artistName, int limit, int offset);

    List<AlbumEntity> findAlbumsByArtistNameOrAlbumName(String keyword);

    List<AlbumEntity> findAlbumByKeyword(String keyword, int limit, int offset);
}
