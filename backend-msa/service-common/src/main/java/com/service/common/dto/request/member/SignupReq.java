package com.service.common.dto.request.member;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignupReq {
    private String email;
    private String password;
    private String name;
    private String nickname;
    private Boolean gender;
    private String birth;
    private String phoneNumber;
    private String address;
    private String role;
    private String socialType;
}
