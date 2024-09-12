package com.sabujaks.irs.domain.resume.model.request;

import jakarta.persistence.Column;
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
