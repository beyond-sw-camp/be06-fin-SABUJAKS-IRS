package com.sabujaks.irs.domain.auth.model.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfoGetRes {
    private String email;
    private String name;
    private String role;
}
