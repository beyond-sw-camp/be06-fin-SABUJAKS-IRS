package com.example.api.domain.auth.model.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfoGetRes {
    private String nickName;
    private String email;
    private String name;
    private String role;
}
