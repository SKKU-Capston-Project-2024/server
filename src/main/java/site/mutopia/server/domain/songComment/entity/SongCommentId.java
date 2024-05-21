package site.mutopia.server.domain.songComment.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class SongCommentId implements Serializable {
    private String writer;
    private String song;
}