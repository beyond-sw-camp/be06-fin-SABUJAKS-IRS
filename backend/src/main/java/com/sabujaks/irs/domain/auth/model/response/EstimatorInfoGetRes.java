package com.sabujaks.irs.domain.auth.model.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class EstimatorInfoGetRes {
    private Long idx;
    private String email; // 이메일
    private String password; // 비밀번호
    private String name;
    private String role; // 접근 권한
    private Boolean emailAuth;
}
