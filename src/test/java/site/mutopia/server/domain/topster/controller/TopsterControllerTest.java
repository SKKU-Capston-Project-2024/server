package site.mutopia.server.domain.topster.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import site.mutopia.server.TestSecurityConfig;
import site.mutopia.server.domain.album.entity.AlbumEntity;
import site.mutopia.server.domain.album.repository.AlbumEntityRepository;
import site.mutopia.server.domain.auth.jwt.TokenProvider;
import site.mutopia.server.domain.topster.dto.*;
import site.mutopia.server.domain.topster.entity.TopsterAlbumEntity;
import site.mutopia.server.domain.topster.entity.TopsterEntity;
import site.mutopia.server.domain.topster.repository.TopsterAlbumRepository;
import site.mutopia.server.domain.topster.repository.TopsterRepository;
import site.mutopia.server.domain.topster.service.TopsterService;
import site.mutopia.server.domain.user.entity.UserEntity;
import site.mutopia.server.domain.user.repository.UserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static site.mutopia.server.util.ReflectionUtil.setFieldValue;

@WebMvcTest(controllers = TopsterController.class)
@ImportAutoConfiguration(exclude = {
        DataSourceAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class,
})
@Import({TopsterService.class, TestSecurityConfig.class})
@MockBean(JpaMetamodelMappingContext.class)
class TopsterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private TopsterRepository topsterRepository;

    @MockBean
    private AlbumEntityRepository albumRepository;

    @MockBean
    private TopsterAlbumRepository topsterAlbumRepository;

    @MockBean
    private TokenProvider tokenProvider;

    @Autowired
    private ObjectMapper objectMapper;

    private UserEntity loggedInUser;

    @BeforeEach
    public void setup() throws Exception {
        // mock authentication
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        Authentication authentication = Mockito.mock(Authentication.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
    }



    @Nested
    class SaveTopster {

        @Test
        void 정상적인_요청을_보냈을때_탑스터_저장_성공() throws Exception {
            // given
            userLogin();

            TopsterSaveReqDto dto = new TopsterSaveReqDto("My Topster", Arrays.asList("album-1", "album-2"));

            when(userRepository.findById(any())).thenReturn(Optional.of(loggedInUser));
            TopsterEntity savedTopster = TopsterEntity.builder()
                    .title(dto.getTopsterTitle())
                    .user(loggedInUser)
                    .build();
            when(topsterRepository.save(any())).thenReturn(savedTopster);
            when(albumRepository.findAllById(any())).thenReturn(
                    List.of(AlbumEntity.builder().id("album-1").build(), AlbumEntity.builder().id("album-2").build())
            );

            setFieldValue(savedTopster, "id", 1L);

            // then
            mockMvc.perform(post("/topster")
                            .contentType("application/json")
                            .header("Authorization", "Bearer mockedToken")
                            .content(objectMapper.writeValueAsString(dto)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.topsterId").value(1L))
                    .andDo(print());
        }

        @Test
        void 사용자가_로그인_하지_않았을때_권한없음을_반환해야한다() throws Exception {
            // given
            TopsterSaveReqDto dto = new TopsterSaveReqDto("My Topster", Arrays.asList("album-1", "album-2"));

            // User Not Logged In
            when(tokenProvider.getUserEntity(Mockito.anyString())).thenReturn(Optional.empty());


            // then
            mockMvc.perform(post("/topster")
                            .contentType("application/json")
                            .header("Authorization", "Bearer mockedToken")
                            .content(objectMapper.writeValueAsString(dto)))
                    .andExpect(status().isUnauthorized())
                    .andDo(print());
        }
    }

    @Nested
    class GetTopsterById {

        @Test
        void 정상적인_요청을_보냈을때_탑스터_조회_성공() throws Exception {
            // when
            userLogin();

            TopsterEntity topster = TopsterEntity.builder().title("My Topster").user(loggedInUser).build();
            setFieldValue(topster, "id", 1L);

            AlbumEntity album1 = AlbumEntity.builder().id("album-1").coverImageUrl("url-1").artistName("artist-1").name("album-name-1").releaseDate("2024-05-03").length(0L).build();
            AlbumEntity album2 = AlbumEntity.builder().id("album-2").coverImageUrl("url-2").artistName("artist-2").name("album-name-2").releaseDate("2024-05-04").length(0L).build();

            when(topsterRepository.findById(any())).thenReturn(Optional.of(topster));
            when(topsterAlbumRepository.findByTopsterId(any())).thenReturn(List.of(
                    TopsterAlbumEntity.builder().album(album1).topster(topster).build(),
                    TopsterAlbumEntity.builder().album(album2).topster(topster).build()
            ));

            mockMvc.perform(get("/topster/1")
                            .contentType("application/json")
                            .header("Authorization", "Bearer mockedToken"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.topster.id").value(topster.getId()))
                    .andExpect(jsonPath("$.topster.title").value(topster.getTitle()))
                    .andExpect(jsonPath("$.user.id").value(loggedInUser.getId()))
                    .andExpect(jsonPath("$.user.username").value(loggedInUser.getUsername()))
                    .andExpect(jsonPath("$.topsterAlbums[0].id").value(album1.getId()))
                    .andExpect(jsonPath("$.topsterAlbums[0].name").value(album1.getName()))
                    .andExpect(jsonPath("$.topsterAlbums[0].artistName").value(album1.getArtistName()))
                    .andExpect(jsonPath("$.topsterAlbums[0].coverImageUrl").value(album1.getCoverImageUrl()))
                    .andExpect(jsonPath("$.topsterAlbums[0].releaseDate").value(album1.getReleaseDate()))
                    .andExpect(jsonPath("$.topsterAlbums[0].length").value(album1.getLength()))
                    .andExpect(jsonPath("$.topsterAlbums[1].id").value(album2.getId()))
                    .andExpect(jsonPath("$.topsterAlbums[1].name").value(album2.getName()))
                    .andExpect(jsonPath("$.topsterAlbums[1].artistName").value(album2.getArtistName()))
                    .andExpect(jsonPath("$.topsterAlbums[1].coverImageUrl").value(album2.getCoverImageUrl()))
                    .andExpect(jsonPath("$.topsterAlbums[1].releaseDate").value(album2.getReleaseDate()))
                    .andExpect(jsonPath("$.topsterAlbums[1].length").value(album2.getLength()))
                    .andDo(print());
        }

        @Test
        void 사용자가_로그인_하지_않았을때도_정상_조회_가능() throws Exception {
            // when

            // User Not Logged In
            when(tokenProvider.getUserEntity(Mockito.anyString())).thenReturn(Optional.empty());

            UserEntity writer = UserEntity.builder().username("user-name").build();
            setFieldValue(writer, "id", "user-id-1234nlaskdjf");

            TopsterEntity topster = TopsterEntity.builder().title("My Topster").user(writer).build();
            setFieldValue(topster, "id", 1L);

            AlbumEntity album1 = AlbumEntity.builder().id("album-1").coverImageUrl("url-1").artistName("artist-1").name("album-name-1").releaseDate("2024-05-03").length(0L).build();
            AlbumEntity album2 = AlbumEntity.builder().id("album-2").coverImageUrl("url-2").artistName("artist-2").name("album-name-2").releaseDate("2024-05-04").length(0L).build();

            when(topsterRepository.findById(any())).thenReturn(Optional.of(topster));
            when(topsterAlbumRepository.findByTopsterId(any())).thenReturn(List.of(
                    TopsterAlbumEntity.builder().album(album1).topster(topster).build(),
                    TopsterAlbumEntity.builder().album(album2).topster(topster).build()
            ));

            mockMvc.perform(get("/topster/1")
                            .contentType("application/json")
                            .header("Authorization", "Bearer mockedToken"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.topster.id").value(topster.getId()))
                    .andExpect(jsonPath("$.topster.title").value(topster.getTitle()))
                    .andExpect(jsonPath("$.user.id").value(writer.getId()))
                    .andExpect(jsonPath("$.user.username").value(writer.getUsername()))
                    .andExpect(jsonPath("$.topsterAlbums[0].id").value(album1.getId()))
                    .andExpect(jsonPath("$.topsterAlbums[0].name").value(album1.getName()))
                    .andExpect(jsonPath("$.topsterAlbums[0].artistName").value(album1.getArtistName()))
                    .andExpect(jsonPath("$.topsterAlbums[0].coverImageUrl").value(album1.getCoverImageUrl()))
                    .andExpect(jsonPath("$.topsterAlbums[0].releaseDate").value(album1.getReleaseDate()))
                    .andExpect(jsonPath("$.topsterAlbums[0].length").value(album1.getLength()))
                    .andExpect(jsonPath("$.topsterAlbums[1].id").value(album2.getId()))
                    .andExpect(jsonPath("$.topsterAlbums[1].name").value(album2.getName()))
                    .andExpect(jsonPath("$.topsterAlbums[1].artistName").value(album2.getArtistName()))
                    .andExpect(jsonPath("$.topsterAlbums[1].coverImageUrl").value(album2.getCoverImageUrl()))
                    .andExpect(jsonPath("$.topsterAlbums[1].releaseDate").value(album2.getReleaseDate()))
                    .andExpect(jsonPath("$.topsterAlbums[1].length").value(album2.getLength()))
                    .andDo(print());
        }
    }

    @Nested
    class RemoveTopsterById {

        @Test
        void 정상적인_요청을_보냈을때_탑스터_삭제_성공() throws Exception {
            userLogin();

            when(topsterRepository.existsByUserIdAndTopsterId(any(), anyLong())).thenReturn(true);

            mockMvc.perform(delete("/topster/1")
                            .contentType("application/json")
                            .header("Authorization", "Bearer mockedToken"))
                    .andExpect(status().isOk())
                    .andDo(print());
        }

        @Test
        void 유저가_다른사람의_탑스터를_삭제하려고_할때_Forbidden_반환() throws Exception {
            userLogin();

            when(topsterRepository.existsByUserIdAndTopsterId(any(), anyLong())).thenReturn(false);

            mockMvc.perform(delete("/topster/1")
                            .contentType("application/json")
                            .header("Authorization", "Bearer mockedToken"))
                    .andExpect(status().isForbidden())
                    .andExpect(content().string("Error: You do not have permission to modify this Topster."))
                    .andDo(print());
        }
    }

    @Nested
    class DeleteAlbumsFromTopster {

        @Test
        void 정상적인_요청을_보냈을때_탑스터에서_앨범_삭제_성공() throws Exception {
            // when
            userLogin();

            TopsterAlbumDeleteReqDto requestDto = new TopsterAlbumDeleteReqDto(Arrays.asList("album-1", "album-2"));
            TopsterEntity topster = TopsterEntity.builder().title("My Topster").user(loggedInUser).build();
            setFieldValue(topster, "id", 1L);

            AlbumEntity album1 = AlbumEntity.builder().id("album-1").build();
            AlbumEntity album2 = AlbumEntity.builder().id("album-2").build();
            AlbumEntity album3 = AlbumEntity.builder().id("album-3").build();

            when(topsterRepository.existsByUserIdAndTopsterId(any(), anyLong())).thenReturn(true);
            when(topsterRepository.findById(anyLong())).thenReturn(Optional.of(topster));
            when(topsterAlbumRepository.findByTopsterId(anyLong())).thenReturn(List.of(
                    TopsterAlbumEntity.builder().topster(topster).album(album3).build()
            ));

            mockMvc.perform(delete("/topster/1/album")
                            .contentType("application/json")
                            .header("Authorization", "Bearer mockedToken")
                            .content(objectMapper.writeValueAsString(requestDto)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.remainAlbumIds[0]").value("album-3"))
                    .andDo(print());
        }

        @Test
        void 유저가_다른_유저의_탑스터에_앨범을_삭제하려_하면_Forbidden() throws Exception {
            userLogin();

            TopsterAlbumDeleteReqDto requestDto = new TopsterAlbumDeleteReqDto(Arrays.asList("album-1", "album-2"));

            when(topsterRepository.existsByUserIdAndTopsterId(any(), anyLong())).thenReturn(false);

            mockMvc.perform(delete("/topster/1/album")
                            .contentType("application/json")
                            .header("Authorization", "Bearer mockedToken")
                            .content(objectMapper.writeValueAsString(requestDto)))
                    .andExpect(status().isForbidden())
                    .andExpect(content().string("Error: You do not have permission to modify this Topster."))
                    .andDo(print());
        }
    }



    @Nested
    class AppendAlbumsInTopster {

        @Test
        void 정상적인_요청을_보냈을때_탑스터에_앨범_추가_성공() throws Exception {
            // when
            userLogin();

            TopsterAlbumAppendReqDto requestDto = new TopsterAlbumAppendReqDto(Arrays.asList("album-3"));
            TopsterEntity topster = TopsterEntity.builder().title("My Topster").user(loggedInUser).build();
            setFieldValue(topster, "id", 1L);

            AlbumEntity album1 = AlbumEntity.builder().id("album-1").coverImageUrl("url-1").artistName("artist-1").name("album-name-1").releaseDate("2024-05-03").length(0L).build();
            AlbumEntity album2 = AlbumEntity.builder().id("album-2").coverImageUrl("url-2").artistName("artist-2").name("album-name-2").releaseDate("2024-05-04").length(0L).build();
            AlbumEntity album3 = AlbumEntity.builder().id("album-3").coverImageUrl("url-3").artistName("artist-3").name("album-name-3").releaseDate("2024-05-05").length(0L).build();

            when(topsterRepository.existsByUserIdAndTopsterId(any(), anyLong())).thenReturn(true);
            when(topsterRepository.findById(anyLong())).thenReturn(Optional.of(topster));
            when(albumRepository.findAllById(any())).thenReturn(List.of(album1, album2));

            when(topsterAlbumRepository.findByTopsterId(anyLong())).thenReturn(List.of(
                    TopsterAlbumEntity.builder().topster(topster).album(album1).build(),
                    TopsterAlbumEntity.builder().topster(topster).album(album2).build(),
                    TopsterAlbumEntity.builder().topster(topster).album(album3).build()
            ));

            // then
            mockMvc.perform(post("/topster/1/album")
                            .contentType("application/json")
                            .header("Authorization", "Bearer mockedToken")
                            .content(objectMapper.writeValueAsString(requestDto)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.remainAlbumIds[0]").value("album-1"))
                    .andExpect(jsonPath("$.remainAlbumIds[1]").value("album-2"))
                    .andExpect(jsonPath("$.remainAlbumIds[2]").value("album-3"))
                    .andDo(print());
        }

        @Test
        void 유저가_다른_유저의_탑스터에_앨범을_추가하려_하면_Forbidden() throws Exception {
            userLogin();

            TopsterAlbumAppendReqDto requestDto = new TopsterAlbumAppendReqDto(Arrays.asList("album-1", "album-2"));

            when(topsterRepository.existsByUserIdAndTopsterId(any(), anyLong())).thenReturn(false);

            mockMvc.perform(post("/topster/1/album")
                            .contentType("application/json")
                            .header("Authorization", "Bearer mockedToken")
                            .content(objectMapper.writeValueAsString(requestDto)))
                    .andExpect(status().isForbidden())
                    .andExpect(content().string("Error: You do not have permission to modify this Topster."))
                    .andDo(print());
        }
    }

    private void userLogin() throws Exception {
        loggedInUser = UserEntity.builder()
                .provider("google")
                .providerId("provider-id-1")
                .email("user@gmail.com")
                .username("username-1")
                .build();
        setFieldValue(loggedInUser, "id", "84102e90-8d58-4b6e-9b44-6ceb6b984c59");
        when(tokenProvider.getUserEntity(Mockito.anyString())).thenReturn(Optional.of(loggedInUser));
    }
}
