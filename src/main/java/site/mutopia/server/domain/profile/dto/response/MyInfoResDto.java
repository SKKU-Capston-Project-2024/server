package site.mutopia.server.domain.profile.dto.response;

import lombok.Builder;

@Builder
public record MyInfoResDto (
        String id,
        String name,
        String profileUrl,
        String bio,
        Boolean isFirstLogin
){}
