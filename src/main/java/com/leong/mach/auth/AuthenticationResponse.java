package com.leong.mach.auth;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthenticationResponse {
    private Integer id;
    private String token;
    private String fullname;
    private String avatarUrl;
}
