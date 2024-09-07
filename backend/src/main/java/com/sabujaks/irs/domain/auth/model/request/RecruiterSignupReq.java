package com.sabujaks.irs.domain.auth.model.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecruiterSignupReq {
    private String email; // 이메일
    private String password; // 비밀번호
    private String name; // 가입자명
    private String phone_number; // 휴대폰번호
    private String role; // 역할
}
