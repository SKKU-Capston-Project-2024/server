package site.mutopia.server.domain.album.repository;


import site.mutopia.server.domain.album.domain.MutopiaAlbum;

public interface AlbumRepository {

    MutopiaAlbum findAlbumById(String albumId);

    MutopiaAlbum findAlbumByAlbumName(String albumName);

    MutopiaAlbum findAlbumByArtistName(String artistName);

    MutopiaAlbum findAlbumByArtistNameOrAlbumName(String keyword);
}
