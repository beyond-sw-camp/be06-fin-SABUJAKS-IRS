package com.sabujaks.irs.domain.auth.model.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthSignupReq {
    private String email; // 이메일
    private String password; // 비밀번호
    private String name; // 지원자명
    private String nickname; // 닉네임
    private Boolean gender; // 성별 0 남성, 1 여성
    private String birth; // 생년월일
    private String phone_number; // 전화 번호
    private String address; // 주소
    private String role; // 접근 권한
    private String socialType; // 소셜 계정 여부
}
