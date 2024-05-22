package site.mutopia.server.domain.song.repository;

import site.mutopia.server.domain.song.dto.SongInfoDto;

import java.util.Optional;

public interface SongCustomRepository {
    Optional<SongInfoDto> findInfoById(String loginUserId, String songId);
}
