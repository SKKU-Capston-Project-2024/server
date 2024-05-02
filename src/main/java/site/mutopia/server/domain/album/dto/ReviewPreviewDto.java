package site.mutopia.server.domain.album.dto;

import java.time.ZonedDateTime;

public record ReviewPreviewDto(
        String reviewId,
        String authorName,
        String authorImg,
        String title,
        String content,
        Long stars,
        ZonedDateTime createdAt
) {
}
