package com.sabujaks.irs.domain.auth.model.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthLoginReq {
    private String email; // 이메일
    private String password; // 비밀번호
    private String role; // 권한 설정
}
