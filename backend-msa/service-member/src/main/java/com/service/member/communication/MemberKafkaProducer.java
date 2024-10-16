package com.service.member.communication;

import com.service.common.dto.kafka.CreateEmailVerificationMsg;
import com.service.common.dto.kafka.DeleteVerificationMsg;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberKafkaProducer {

    private final KafkaTemplate<String, CreateEmailVerificationMsg> createEmailVerificationMsgKafkaTemplate;
    private final KafkaTemplate<String, DeleteVerificationMsg> deleteVerificationMsgKafkaTemplate;

    public void createEmailVerificationMsg(CreateEmailVerificationMsg message) { createEmailVerificationMsgKafkaTemplate.send("CREATE_EMAIL_VERIFICATION", message); }

    public void deleteEmailVerificationMsg(DeleteVerificationMsg message) { deleteVerificationMsgKafkaTemplate.send("DELETE_VERIFICATION", message); }

}
