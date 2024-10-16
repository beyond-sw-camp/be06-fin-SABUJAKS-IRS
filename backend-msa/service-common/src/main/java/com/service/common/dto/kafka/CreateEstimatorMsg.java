package com.service.common.dto.kafka;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateEstimatorMsg {
    private Long recruiterIdx;
    private String name;
    private String email;
    private String password;
    private String role;
}
