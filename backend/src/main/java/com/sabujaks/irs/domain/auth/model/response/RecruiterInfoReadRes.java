package com.sabujaks.irs.domain.auth.model.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecruiterInfoReadRes {
    private String managerName; // 인사담당자명
    private String managerContact; // 인사담당자 연락처
    private String phone1;
    private String phone2;
    private String phone3;
    private String managerEmail; // 인사담당자 이메일
}