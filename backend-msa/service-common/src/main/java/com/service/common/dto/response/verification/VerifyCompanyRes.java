package com.service.common.dto.response.verification;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VerifyCompanyRes {
    private String recruiterEmail;
    private Boolean isCompanyAuth;
}
