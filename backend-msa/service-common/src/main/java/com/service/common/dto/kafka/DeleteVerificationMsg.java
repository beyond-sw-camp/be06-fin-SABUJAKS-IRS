package com.service.common.dto.kafka;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
// Kafka: 검증 서비스의 이메일 인증 값을 지우기 위한 메시지
public class DeleteVerificationMsg {
    private String email;
    private String role;
}
