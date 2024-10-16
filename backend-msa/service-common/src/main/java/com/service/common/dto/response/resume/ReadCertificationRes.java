package com.service.common.dto.response.resume;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReadCertificationRes {
    private String certName;
    private String organization;
    private String takingAt;
}
