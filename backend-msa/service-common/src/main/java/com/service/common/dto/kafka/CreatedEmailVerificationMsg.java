package com.service.common.dto.kafka;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatedEmailVerificationMsg {
    private String email;
    private String role;
    private Boolean isEmailAuth;
}
