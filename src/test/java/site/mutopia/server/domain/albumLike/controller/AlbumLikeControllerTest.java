package site.mutopia.server.domain.albumLike.controller;

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
import site.mutopia.server.domain.albumLike.dto.AlbumLikeStatusResDto;
import site.mutopia.server.domain.albumLike.dto.AlbumLikeToggleResDto;
import site.mutopia.server.domain.albumLike.repository.AlbumLikeRepository;
import site.mutopia.server.domain.albumLike.service.AlbumLikeService;
import site.mutopia.server.domain.auth.jwt.TokenProvider;
import site.mutopia.server.domain.user.entity.UserEntity;
import site.mutopia.server.domain.user.repository.UserRepository;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static site.mutopia.server.util.ReflectionUtil.setFieldValue;

@WebMvcTest(controllers = AlbumLikeController.class)
@ImportAutoConfiguration(exclude = {
        DataSourceAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class,
})
@Import({AlbumLikeService.class, TestSecurityConfig.class})
@MockBean(JpaMetamodelMappingContext.class)
class AlbumLikeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AlbumLikeRepository albumLikeRepository;

    @MockBean
    private AlbumRepository albumRepository;

    @MockBean
    private UserRepository userRepository;

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
    class ToggleLikeToAlbum {

        @Test
        void 원래_좋아요가_꺼져_있었는데_좋아요를_누른_경우() throws Exception {
            // given
            userLogin();
            String albumId = "album-123";
            when(albumLikeRepository.existsByAlbumIdAndUserId(albumId, loggedInUser.getId())).thenReturn(true);
            when(albumRepository.countLikesByAlbumId(albumId)).thenReturn(10L); // 좋아요 카운트 10

            // then
            mockMvc.perform(post("/album/" + albumId + "/like/toggle")
                            .contentType("application/json")
                            .header("Authorization", "Bearer mockedToken"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.likeStatus").value(AlbumLikeToggleResDto.AlbumLikeToggleResStatus.ON.name()))
                    .andExpect(jsonPath("$.likeCount").value(10L))
                    .andDo(print());
        }

        @Test
        void 원래_좋아요가_켜져_있었는데_좋아요를_누른_경우() throws Exception {
            // given
            userLogin();
            String albumId = "album-123";
            when(albumLikeRepository.existsByAlbumIdAndUserId(albumId, loggedInUser.getId())).thenReturn(false);
            when(userRepository.findById(loggedInUser.getId())).thenReturn(Optional.of(loggedInUser));
            when(albumRepository.findAlbumById(any())).thenReturn(Optional.of(AlbumEntity.builder().build()));

            when(albumRepository.countLikesByAlbumId(albumId)).thenReturn(10L); // 좋아요 카운트 10

            // then
            mockMvc.perform(post("/album/" + albumId + "/like/toggle")
                            .contentType("application/json")
                            .header("Authorization", "Bearer mockedToken"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.likeStatus").value(AlbumLikeToggleResDto.AlbumLikeToggleResStatus.OFF.name()))
                    .andExpect(jsonPath("$.likeCount").value(10L))
                    .andDo(print());
        }

        @Test
        void 사용자가_로그인_하지_않았을때_권한없음을_반환해야한다() throws Exception {
            // given
            String albumId = "album-123";

            // User Not Logged In
            when(tokenProvider.getUserEntity(Mockito.anyString())).thenReturn(Optional.empty());

            // then
            mockMvc.perform(post("/album/" + albumId + "/like/toggle")
                            .contentType("application/json")
                            .header("Authorization", "Bearer mockedToken"))
                    .andExpect(status().isUnauthorized())
                    .andDo(print());
        }
    }


    @Nested
    class GetAlbumLikeStatus {

        @Test
        void 로그인한_사용자에_대한_앨범_좋아요_상태를_반환해야한다_좋아요_눌린_경우() throws Exception {
            // given
            userLogin();
            String albumId = "album-123";
            when(albumRepository.countLikesByAlbumId(albumId)).thenReturn(10L);
            when(albumLikeRepository.existsByAlbumIdAndUserId(albumId, loggedInUser.getId())).thenReturn(true);

            // then
            mockMvc.perform(get("/album/" + albumId + "/like/status")
                            .contentType("application/json")
                            .header("Authorization", "Bearer mockedToken"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.isUserLoggedIn").value(AlbumLikeStatusResDto.IsUserLoggedIn.YES.name()))
                    .andExpect(jsonPath("$.likeStatus").value(AlbumLikeStatusResDto.AlbumLikeToggleStatus.ON.name()))
                    .andExpect(jsonPath("$.likeCount").value(10L))
                    .andDo(print());
        }

        @Test
        void 로그인한_사용자에_대한_앨범_좋아요_상태를_반환해야한다_좋아요_안눌린_경우() throws Exception {
            // given
            userLogin();
            String albumId = "album-123";
            when(albumRepository.countLikesByAlbumId(albumId)).thenReturn(10L);
            when(albumLikeRepository.existsByAlbumIdAndUserId(albumId, loggedInUser.getId())).thenReturn(false);

            // then
            mockMvc.perform(get("/album/" + albumId + "/like/status")
                            .contentType("application/json")
                            .header("Authorization", "Bearer mockedToken"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.isUserLoggedIn").value(AlbumLikeStatusResDto.IsUserLoggedIn.YES.name()))
                    .andExpect(jsonPath("$.likeStatus").value(AlbumLikeStatusResDto.AlbumLikeToggleStatus.OFF.name()))
                    .andExpect(jsonPath("$.likeCount").value(10L))
                    .andDo(print());
        }

        @Test
        void 로그인하지_않은_사용자에_대한_앨범_좋아요_상태를_반환해야한다() throws Exception {
            // given
            String albumId = "album-123";

            // User Not Logged In
            when(tokenProvider.getUserEntity(Mockito.anyString())).thenReturn(Optional.empty());

            when(albumRepository.countLikesByAlbumId(albumId)).thenReturn(10L);

            // then
            mockMvc.perform(get("/album/" + albumId + "/like/status")
                            .contentType("application/json")
                            .header("Authorization", "Bearer mockedToken"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.isUserLoggedIn").value(AlbumLikeStatusResDto.IsUserLoggedIn.NO.name()))
                    .andExpect(jsonPath("$.likeStatus").value(AlbumLikeStatusResDto.AlbumLikeToggleStatus.NULL.name()))
                    .andExpect(jsonPath("$.likeCount").value(10L))
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
