package com.service.verification.communication;

import com.service.common.dto.kafka.CreatedEmailVerificationMsg;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VerificationKafkaProducer {

    private final KafkaTemplate<String, CreatedEmailVerificationMsg> createdEmailVerificationMsgKafkaTemplate;

    public void createdEmailVerificationMsg(CreatedEmailVerificationMsg message) { createdEmailVerificationMsgKafkaTemplate.send("CREATED_EMAIL_VERIFICATION", message); }
}
