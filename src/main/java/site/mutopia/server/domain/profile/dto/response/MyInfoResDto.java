package site.mutopia.server.domain.profile.dto.response;

import lombok.Getter;
import lombok.Setter;

public record MyInfoResDto (
        String id,
        String name,
        String profileUrl
){}
