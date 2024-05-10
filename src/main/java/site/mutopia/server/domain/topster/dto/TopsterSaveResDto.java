package site.mutopia.server.domain.topster.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TopsterSaveResDto {
    private Long topsterId;
}