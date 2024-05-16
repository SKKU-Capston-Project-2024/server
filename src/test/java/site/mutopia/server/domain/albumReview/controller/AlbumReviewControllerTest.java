package site.mutopia.server.domain.albumReview.controller;

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
import site.mutopia.server.domain.album.repository.AlbumRepository;
import site.mutopia.server.domain.albumReview.dto.AlbumReviewInfoDto;
import site.mutopia.server.domain.albumReview.dto.AlbumReviewSaveReqDto;
import site.mutopia.server.domain.albumReview.entity.AlbumReviewEntity;
import site.mutopia.server.domain.albumReview.repository.AlbumReviewRepository;
import site.mutopia.server.domain.albumReview.service.AlbumReviewService;
import site.mutopia.server.domain.albumReviewLike.entity.AlbumReviewLikeEntity;
import site.mutopia.server.domain.albumReviewLike.repository.AlbumReviewLikeRepository;
import site.mutopia.server.domain.auth.jwt.TokenProvider;
import site.mutopia.server.domain.user.entity.UserEntity;
import site.mutopia.server.domain.user.repository.UserRepository;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static site.mutopia.server.util.ReflectionUtil.setFieldValue;

@WebMvcTest(controllers = AlbumReviewController.class)
@ImportAutoConfiguration(exclude = {
        DataSourceAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class,
})
@Import({AlbumReviewService.class, TestSecurityConfig.class})
@MockBean(JpaMetamodelMappingContext.class)
class AlbumReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AlbumReviewRepository albumReviewRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private AlbumRepository albumRepository;

    @MockBean
    private TokenProvider tokenProvider;

    @MockBean
    private AlbumReviewLikeRepository albumReviewLikeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private UserEntity loggedInUser;

    @BeforeEach
    public void setup() throws Exception{
        // mock authentication
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        Authentication authentication = Mockito.mock(Authentication.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
    }

    @Nested
    class SaveAlbumReview {

        @Test
        void shouldCreateAlbumReviewWhenRequestIsValid() throws Exception {
            // given
            userLogin();
            AlbumReviewSaveReqDto saveReqDto = AlbumReviewSaveReqDto.builder()
                    .title("Great Album")
                    .content("This is a great album with fantastic songs.")
                    .rating(5)
                    .albumId("album-123")
                    .build();

            when(albumReviewRepository.findByWriterId(anyString())).thenReturn(Optional.empty());
            when(userRepository.findById(anyString())).thenReturn(Optional.of(loggedInUser));
            when(albumRepository.findAlbumById(anyString())).thenReturn(Optional.of(AlbumEntity.builder().id(saveReqDto.getAlbumId()).build()));

            AlbumReviewEntity albumReview = AlbumReviewEntity.builder()
                    .title(saveReqDto.getTitle())
                    .content(saveReqDto.getContent())
                    .rating(saveReqDto.getRating())
                    .album(AlbumEntity.builder().build())
                    .build();
            when(albumReviewRepository.save(Mockito.any(AlbumReviewEntity.class)))
                    .thenReturn(albumReview);
            setFieldValue(albumReview, "id", 1L);


            // then
            mockMvc.perform(post("/album/review")
                            .contentType("application/json")
                            .content(objectMapper.writeValueAsString(saveReqDto))
                            .header("Authorization", "Bearer mockedToken"))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.albumReviewId").isNumber())
                    .andDo(print());
        }


        @Test
        void userCantReviewSameAlbumTwice() throws Exception {
            // given
            userLogin();
            AlbumReviewSaveReqDto dto = AlbumReviewSaveReqDto.builder()
                    .title("Great Album")
                    .content("This is a great album with fantastic songs.")
                    .rating(5)
                    .albumId("album-123")
                    .build();

            when(albumReviewRepository.findByWriterId(anyString()))
                    .thenReturn(Optional.of(AlbumReviewEntity.builder().build()));

            // then
            mockMvc.perform(post("/album/review")
                            .contentType("application/json")
                            .content(objectMapper.writeValueAsString(dto))
                            .header("Authorization", "Bearer mockedToken"))
                    .andExpect(status().is4xxClientError())
                    .andDo(print());
        }

        @Test
        void shouldReturnNotFoundWhenWriterNotFound() throws Exception {
            // given
            userLogin();
            AlbumReviewSaveReqDto saveReqDto = AlbumReviewSaveReqDto.builder()
                    .title("Great Album")
                    .content("This is a great album with fantastic songs.")
                    .rating(5)
                    .albumId("album-123")
                    .build();

            when(albumReviewRepository.findByWriterId(anyString())).thenReturn(Optional.empty());
            when(userRepository.findById(any())).thenReturn(Optional.empty());

            // then
            mockMvc.perform(post("/album/review")
                            .contentType("application/json")
                            .content(objectMapper.writeValueAsString(saveReqDto))
                            .header("Authorization", "Bearer mockedToken"))
                    .andExpect(status().isNotFound())
                    .andDo(print());
        }

        @Test
        void shouldReturnNotFoundWhenAlbumNotFound() throws Exception {
            // given
            userLogin();
            AlbumReviewSaveReqDto saveReqDto = AlbumReviewSaveReqDto.builder()
                    .title("Great Album")
                    .content("This is a great album with fantastic songs.")
                    .rating(5)
                    .albumId("album-123")
                    .build();

            when(albumReviewRepository.findByWriterId(anyString())).thenReturn(Optional.empty());
            when(userRepository.findById(any())).thenReturn(Optional.of(loggedInUser));
            when(albumRepository.findAlbumById(anyString())).thenReturn(Optional.empty());

            // when
            mockMvc.perform(post("/album/review")
                            .contentType("application/json")
                            .content(objectMapper.writeValueAsString(saveReqDto))
                            .header("Authorization", "Bearer mockedToken"))
                    .andExpect(status().isNotFound())
                    .andDo(print());
        }
    }

    @Nested
    class GetAlbumReviewByAlbumReviewId {

        @Test
        void shouldReturnAlbumReviewWhenUserLoggedInAndUserLikesOwnReview() throws Exception {
            // given
            userLogin();

            AlbumReviewInfoDto dto = new AlbumReviewInfoDto(1L, "review-title", "content", 5, "album-id", "writer-id", "username", "album-name", "artist-name", "cover-img-url", "2024-05-03", 0L, 3L, 5L);
            when(albumReviewRepository.findAlbumReviewInfoDto(any())).thenReturn(Optional.of(dto));
            when(albumReviewLikeRepository.findById(any())).thenReturn(Optional.of(AlbumReviewLikeEntity.builder().build()));


            // then
            mockMvc.perform(get("/album/review/1")
                            .contentType("application/json")
                            .header("Authorization", "Bearer mockedToken"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.review.id").value(dto.getReview().getId()))
                    .andExpect(jsonPath("$.review.title").value(dto.getReview().getTitle()))
                    .andExpect(jsonPath("$.review.content").value(dto.getReview().getContent()))
                    .andExpect(jsonPath("$.review.rating").value(dto.getReview().getRating()))
                    .andExpect(jsonPath("$.review.isLiked").value(true))
                    .andExpect(jsonPath("$.writer.id").value(dto.getWriter().getId()))
                    .andExpect(jsonPath("$.writer.username").value(dto.getWriter().getUsername()))
                    .andExpect(jsonPath("$.album.id").value(dto.getAlbum().getId()))
                    .andExpect(jsonPath("$.album.name").value(dto.getAlbum().getName()))
                    .andExpect(jsonPath("$.album.artistName").value(dto.getAlbum().getArtistName()))
                    .andExpect(jsonPath("$.album.coverImageUrl").value(dto.getAlbum().getCoverImageUrl()))
                    .andExpect(jsonPath("$.album.releaseDate").value(dto.getAlbum().getReleaseDate()))
                    .andExpect(jsonPath("$.album.length").value(dto.getAlbum().getLength()))
                    .andExpect(jsonPath("$.album.totalReviewCount").value(dto.getAlbum().getTotalReviewCount()))
                    .andExpect(jsonPath("$.album.totalLikeCount").value(dto.getAlbum().getTotalLikeCount()))
                    .andDo(print());
        }

        @Test
        void shouldReturnAlbumReviewWhenUserLoggedInAndUserDoesntLikesOwnReview() throws Exception {
            // given
            userLogin();

            AlbumReviewInfoDto dto = new AlbumReviewInfoDto(1L, "review-title", "content", 5, "album-id", "writer-id", "username", "album-name", "artist-name", "cover-img-url", "2024-05-03", 0L, 3L, 5L);
            when(albumReviewRepository.findAlbumReviewInfoDto(any())).thenReturn(Optional.of(dto));
            when(albumReviewLikeRepository.findById(any())).thenReturn(Optional.empty());

            // then
            mockMvc.perform(get("/album/review/1")
                            .contentType("application/json")
                            .header("Authorization", "Bearer mockedToken"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.review.id").value(dto.getReview().getId()))
                    .andExpect(jsonPath("$.review.title").value(dto.getReview().getTitle()))
                    .andExpect(jsonPath("$.review.content").value(dto.getReview().getContent()))
                    .andExpect(jsonPath("$.review.rating").value(dto.getReview().getRating()))
                    .andExpect(jsonPath("$.review.isLiked").value(false))
                    .andExpect(jsonPath("$.writer.id").value(dto.getWriter().getId()))
                    .andExpect(jsonPath("$.writer.username").value(dto.getWriter().getUsername()))
                    .andExpect(jsonPath("$.album.id").value(dto.getAlbum().getId()))
                    .andExpect(jsonPath("$.album.name").value(dto.getAlbum().getName()))
                    .andExpect(jsonPath("$.album.artistName").value(dto.getAlbum().getArtistName()))
                    .andExpect(jsonPath("$.album.coverImageUrl").value(dto.getAlbum().getCoverImageUrl()))
                    .andExpect(jsonPath("$.album.releaseDate").value(dto.getAlbum().getReleaseDate()))
                    .andExpect(jsonPath("$.album.length").value(dto.getAlbum().getLength()))
                    .andExpect(jsonPath("$.album.totalReviewCount").value(dto.getAlbum().getTotalReviewCount()))
                    .andExpect(jsonPath("$.album.totalLikeCount").value(dto.getAlbum().getTotalLikeCount()))
                    .andDo(print());
        }

        @Test
        void shouldReturnAlbumReviewWhenUserNotLoggedIn() throws Exception {
            // given

            // User Not Logged In
            when(tokenProvider.getUserEntity(Mockito.anyString())).thenReturn(Optional.empty());

            AlbumReviewInfoDto dto = new AlbumReviewInfoDto(1L, "review-title", "content", 5, "album-id", "writer-id", "username", "album-name", "artist-name", "cover-img-url", "2024-05-03", 0L, 3L, 5L);
            when(albumReviewRepository.findAlbumReviewInfoDto(any())).thenReturn(Optional.of(dto));
            when(albumReviewLikeRepository.findById(any())).thenReturn(Optional.of(AlbumReviewLikeEntity.builder().build()));

            // then
            mockMvc.perform(get("/album/review/1")
                            .contentType("application/json")
                            .header("Authorization", "Bearer mockedToken"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.review.id").value(dto.getReview().getId()))
                    .andExpect(jsonPath("$.review.title").value(dto.getReview().getTitle()))
                    .andExpect(jsonPath("$.review.content").value(dto.getReview().getContent()))
                    .andExpect(jsonPath("$.review.rating").value(dto.getReview().getRating()))
                    .andExpect(jsonPath("$.review.isLiked").value(false))
                    .andExpect(jsonPath("$.writer.id").value(dto.getWriter().getId()))
                    .andExpect(jsonPath("$.writer.username").value(dto.getWriter().getUsername()))
                    .andExpect(jsonPath("$.album.id").value(dto.getAlbum().getId()))
                    .andExpect(jsonPath("$.album.name").value(dto.getAlbum().getName()))
                    .andExpect(jsonPath("$.album.artistName").value(dto.getAlbum().getArtistName()))
                    .andExpect(jsonPath("$.album.coverImageUrl").value(dto.getAlbum().getCoverImageUrl()))
                    .andExpect(jsonPath("$.album.releaseDate").value(dto.getAlbum().getReleaseDate()))
                    .andExpect(jsonPath("$.album.length").value(dto.getAlbum().getLength()))
                    .andExpect(jsonPath("$.album.totalReviewCount").value(dto.getAlbum().getTotalReviewCount()))
                    .andExpect(jsonPath("$.album.totalLikeCount").value(dto.getAlbum().getTotalLikeCount()))
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