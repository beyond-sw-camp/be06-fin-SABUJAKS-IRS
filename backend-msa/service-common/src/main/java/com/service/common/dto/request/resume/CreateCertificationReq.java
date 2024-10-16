package com.service.common.dto.request.resume;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateCertificationReq {
    private String certName;
    private String organization;
    private String takingAt;
}
