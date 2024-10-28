package com.example.api.domain.resume.model.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CertificationCreateReq {
    private String certName;
    private String organization;
    private String takingAt;
}
