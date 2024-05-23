package site.mutopia.server.domain.auth.entity;

import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode
public class GoogleTokenId implements Serializable {
    private String user;
    private GoogleTokenType tokenType;
}
