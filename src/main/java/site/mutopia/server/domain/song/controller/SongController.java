package site.mutopia.server.domain.song.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import site.mutopia.server.domain.song.dto.SongSearchResDto;
import site.mutopia.server.domain.song.service.SongService;
import site.mutopia.server.spotify.dto.track.SearchTracksDto;

import java.util.List;


@RestController
@RequiredArgsConstructor
@Tag(name = "Song", description = "곡 관련 API")
@RequestMapping("/song")
public class SongController {

    private final SongService songService;

    @GetMapping("/search")
    public List<SongSearchResDto> search(
            @RequestParam("keyword") String keyword,
            @RequestParam("offset") int offset
    )
    {
        return songService.search(keyword, offset);
    }
}
