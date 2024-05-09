package site.mutopia.server.domain.topster.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.mutopia.server.domain.album.entity.AlbumEntity;
import site.mutopia.server.domain.album.repository.AlbumEntityRepository;
import site.mutopia.server.domain.topster.dto.TopsterInfoDto;
import site.mutopia.server.domain.topster.dto.TopsterInfoDto.TopsterAlbumInfoDto;
import site.mutopia.server.domain.topster.dto.TopsterInfoDto.TopsterInfoDetailDto;
import site.mutopia.server.domain.topster.dto.TopsterInfoDto.UserInfoDto;
import site.mutopia.server.domain.topster.dto.TopsterSaveReqDto;
import site.mutopia.server.domain.topster.entity.TopsterAlbumEntity;
import site.mutopia.server.domain.topster.entity.TopsterEntity;
import site.mutopia.server.domain.topster.exception.TopsterNotFoundException;
import site.mutopia.server.domain.topster.repository.TopsterAlbumRepository;
import site.mutopia.server.domain.topster.repository.TopsterRepository;
import site.mutopia.server.domain.user.entity.UserEntity;
import site.mutopia.server.domain.user.exception.UserNotFoundException;
import site.mutopia.server.domain.user.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TopsterService {

    private final UserRepository userRepository;
    private final TopsterRepository topsterRepository;
    private final AlbumEntityRepository albumRepository;
    private final TopsterAlbumRepository topsterAlbumRepository;

    public TopsterEntity saveTopster(String userId, TopsterSaveReqDto dto) {
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found. userId: " + userId + " does not exist."));

        TopsterEntity savedTopster = topsterRepository.save(TopsterEntity.builder()
                .title(dto.getTopsterTitle())
                .user(user)
                .build());

        // (저장하고자 하는 Topster)에 속한 ALBUM들이 이미 DB에 저장되어 있어야 함
        List<AlbumEntity> albums = albumRepository.findAllById(dto.getAlbumIds());

        albums.stream()
                .map(album -> TopsterAlbumEntity.builder()
                        .album(album)
                        .topster(savedTopster)
                        .build())
                .forEach(topsterAlbum -> topsterAlbumRepository.save(topsterAlbum));

        return savedTopster;
    }

    // 나중에 성능 튜닝
    public TopsterInfoDto getTopsterInfo(Long topsterId) {
        TopsterEntity topsterEntity = topsterRepository.findById(topsterId)
                .orElseThrow(() -> new TopsterNotFoundException("Topster not found. TopsterId: " + topsterId + " does not exist."));

        UserInfoDto userInfoDto = UserInfoDto.builder().id(topsterEntity.getUser().getId()).username(topsterEntity.getUser().getUsername()).build();
        TopsterInfoDetailDto topsterInfoDto = TopsterInfoDetailDto.builder().id(topsterEntity.getId()).title(topsterEntity.getTitle()).build();

        List<TopsterAlbumEntity> topsterAlbumEntities = topsterAlbumRepository.findByTopsterId(topsterEntity.getId());
        List<TopsterAlbumInfoDto> topsterAlbumInfoDtoList = topsterAlbumEntities.stream()
                .map(topsterAlbum ->
                        TopsterAlbumInfoDto.builder()
                                .id(topsterAlbum.getAlbum().getId())
                                .name(topsterAlbum.getAlbum().getName())
                                .artistName(topsterAlbum.getAlbum().getArtistName())
                                .coverImageUrl(topsterAlbum.getAlbum().getCoverImageUrl())
                                .releaseDate(topsterAlbum.getAlbum().getReleaseDate())
                                .length(topsterAlbum.getAlbum().getLength())
                                .build())
                .collect(Collectors.toList());


        return TopsterInfoDto.builder()
                .topster(topsterInfoDto)
                .user(userInfoDto)
                .topsterAlbums(topsterAlbumInfoDtoList)
                .build();
    }

    public boolean userOwnsTopster(String userId, Long topsterId) {
        return topsterRepository.existsByUserIdAndTopsterId(userId, topsterId);
    }

    public List<String> deleteAlbumsFromTopster(Long topsterId, List<String> albumIds) {
        topsterAlbumRepository.deleteByTopsterIdAndAlbumIds(topsterId, albumIds);

        // return remain albums in topster
        return topsterAlbumRepository.findByTopsterId(topsterId).stream().map(topsterAlbum -> topsterAlbum.getAlbum().getId()).toList();
    }

    public List<String> appendAlbumsInTopster(Long topsterId, List<String> albumIds) {
        TopsterEntity topster = topsterRepository.findById(topsterId)
                .orElseThrow(() -> new TopsterNotFoundException("Topster not found. TopsterId: " + topsterId + " does not exist."));

        List<AlbumEntity> albums = albumRepository.findAllById(albumIds);

        List<TopsterAlbumEntity> topsterAlbums = albums.stream().map(album -> TopsterAlbumEntity.builder()
                .album(album)
                .topster(topster)
                .build()).toList();

        topsterAlbumRepository.saveAll(topsterAlbums);

        // return remain albums in topster
        return topsterAlbumRepository.findByTopsterId(topsterId).stream().map(topsterAlbum -> topsterAlbum.getAlbum().getId()).toList();
    }
}
