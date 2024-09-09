package com.sabujaks.irs.domain.auth.model.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyVerifyReq {
    private String crn;      // 사업자 번호
    private String opened_at;  // 시작 날짜
    private String ceo_name;      // 대표자 이름
    private String recruiter_email; // 채용 담당자 이메일
    private String company_secret_code; // 기업 비밀 코드
}
