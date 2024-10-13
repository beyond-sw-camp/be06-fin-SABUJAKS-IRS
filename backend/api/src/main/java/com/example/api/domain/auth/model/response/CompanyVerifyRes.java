package com.example.api.domain.auth.model.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyVerifyRes {
    private String recruiter_email; // 채용 담당자 이메일
    private Boolean auth_success; // 인증 성공 여부
}
