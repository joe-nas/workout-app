package io.github.joenas.workoutapp.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class OauthDetails {

    private String oauthProvider;
    private String oauthId;
}
