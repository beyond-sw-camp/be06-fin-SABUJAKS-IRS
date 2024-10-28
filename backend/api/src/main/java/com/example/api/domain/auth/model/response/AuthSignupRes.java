package com.example.api.domain.auth.model.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthSignupRes {
    private Long idx;
    private String email;
    private Boolean inactive;
    private Boolean email_auth;
    private String role;
}
