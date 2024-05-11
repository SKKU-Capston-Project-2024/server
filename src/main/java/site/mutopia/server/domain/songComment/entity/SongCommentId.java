package site.mutopia.server.domain.songComment.entity;

import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode
public class SongCommentId implements Serializable {
    private String writer;
    private String song;
}