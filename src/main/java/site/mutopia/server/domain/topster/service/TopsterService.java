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
import site.mutopia.server.domain.topster.exception.TopsterAlreadyExistException;
import site.mutopia.server.domain.topster.exception.TopsterNotFoundException;
import site.mutopia.server.domain.topster.repository.TopsterAlbumRepository;
import site.mutopia.server.domain.topster.repository.TopsterRepository;
import site.mutopia.server.domain.user.entity.UserEntity;
import site.mutopia.server.domain.user.exception.UserNotFoundException;
import site.mutopia.server.domain.user.repository.UserRepository;

import java.util.List;
import java.util.Optional;
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
        topsterRepository.findByUserId(userId).ifPresent(t -> {
            throw new TopsterAlreadyExistException("userId: " + userId + " already has topster");
        });

        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found. userId: " + userId + " does not exist."));

        TopsterEntity savedTopster = topsterRepository.save(TopsterEntity.builder()
                .user(user)
                .build());

        List<AlbumEntity> albums = albumRepository.findAllById(dto.getAlbumIds());

        albums.stream()
                .map(album -> TopsterAlbumEntity.builder()
                        .album(album)
                        .topster(savedTopster)
                        .build())
                .forEach(topsterAlbumRepository::save);

        return savedTopster;
    }

    // TODO: 나중에 성능 튜닝
    public TopsterInfoDto getTopsterInfoByUserId(String userId) {
        TopsterEntity topsterEntity = topsterRepository.findByUserId(userId)
                .orElseThrow(() -> new TopsterNotFoundException("Topster that matches to userId: " + userId + " does not exist."));

        UserInfoDto userInfoDto = UserInfoDto.builder().id(topsterEntity.getUser().getId()).username(topsterEntity.getUser().getUsername()).build();
        TopsterInfoDetailDto topsterInfoDto = TopsterInfoDetailDto.builder().id(topsterEntity.getId()).build();

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

    public List<String> deleteAlbumsFromTopster(String userId, List<String> albumIds) {
        TopsterEntity topster = topsterRepository.findByUserId(userId)
                .orElseThrow(() -> new TopsterNotFoundException("user: " + userId + " doesn't have topster"));

        topsterAlbumRepository.deleteByTopsterIdAndAlbumIds(topster.getId(), albumIds);

        // return remain albums in topster
        return topsterAlbumRepository.findByTopsterId(topster.getId()).stream().map(topsterAlbum -> topsterAlbum.getAlbum().getId()).toList();
    }

    public List<String> appendAlbumsInTopster(UserEntity user, List<String> albumIds) {
        TopsterEntity topster = topsterRepository.findByUserId(user.getId())
                .orElse(null);

        if (topster == null) {
            TopsterEntity newTopster = TopsterEntity.builder().user(user).build();
            topster = topsterRepository.save(newTopster);
        }

        List<AlbumEntity> albums = albumRepository.findAllById(albumIds);

        TopsterEntity finalTopster = topster;

        List<TopsterAlbumEntity> topsterAlbums = albums.stream().map(album -> TopsterAlbumEntity.builder()
                .album(album)
                .topster(finalTopster)
                .build()).toList();

        topsterAlbumRepository.saveAll(topsterAlbums);

        // return remain albums in topster
        return topsterAlbumRepository.findByTopsterId(topster.getId()).stream().map(topsterAlbum -> topsterAlbum.getAlbum().getId()).toList();
    }

    public void removeTopsterByUserId(String userId) {
        Optional<TopsterEntity> topster = topsterRepository.findByUserId(userId);
        if(topster.isEmpty()) return;

        topsterAlbumRepository.deleteByTopsterId(topster.get().getId());
        topsterRepository.deleteById(topster.get().getId());
    }
}
