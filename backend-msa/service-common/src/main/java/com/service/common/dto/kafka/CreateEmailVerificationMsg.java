package com.service.common.dto.kafka;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
// Kafka: 검증 서비스 이메일 전송 요청 메시지
public class CreateEmailVerificationMsg {
    private String email;
    private Boolean isInactive;
    private String role;
}
