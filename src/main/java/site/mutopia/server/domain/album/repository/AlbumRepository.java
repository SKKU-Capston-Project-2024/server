package site.mutopia.server.domain.album.repository;


import site.mutopia.server.domain.album.domain.MutopiaAlbum;

import java.util.List;

public interface AlbumRepository {

    MutopiaAlbum findAlbumById(String albumId);

    MutopiaAlbum findAlbumByAlbumName(String albumName);

    List<MutopiaAlbum> findAlbumByArtistName(String artistName, int limit, int offset);

    MutopiaAlbum findAlbumByArtistNameOrAlbumName(String keyword);


    List<MutopiaAlbum> findAlbumByKeyword(String keyword, int limit, int offset);
}
