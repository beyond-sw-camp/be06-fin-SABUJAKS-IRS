package com.service.common.dto.request.verification;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
// TODO: 클라이언트 수정해야됨
public class VerifyCompanyReq {
    private String crn;
    private String openedAt;
    private String ceoName;
    private String recruiterEmail;
    private String companySecretCode;
}
