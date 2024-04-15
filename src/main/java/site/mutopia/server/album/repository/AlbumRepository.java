package site.mutopia.server.album.repository;


import site.mutopia.server.album.domain.MutopiaAlbum;

public interface AlbumRepository {

    MutopiaAlbum findAlbumById(String albumId);

    MutopiaAlbum findAlbumByAlbumName(String albumName);

    MutopiaAlbum findAlbumByArtistName(String artistName);

    MutopiaAlbum findAlbumByArtistNameOrAlbumName(String keyword);
}
