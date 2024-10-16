package com.service.verification.communication;

import com.service.common.dto.kafka.CreateEmailVerificationMsg;
import com.service.common.base.BaseException;
import com.service.common.dto.kafka.DeleteVerificationMsg;
import com.service.verification.service.VerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Component
@Service
@RequiredArgsConstructor
public class VerificationKafkaConsumer {

    private final VerificationService verificationService;

    @KafkaListener(topics = "CREATE_EMAIL_VERIFICATION", groupId = "verify1", containerFactory = "createEmailVerificationMsgKafkaListenerContainerFactory")
    public void receiveCreateEmailVerificationMsg(CreateEmailVerificationMsg message) throws IOException, BaseException {
        verificationService.sendMail(message);
    }

    @KafkaListener(topics = "DELETE_VERIFICATION", groupId = "verify2", containerFactory = "deleteVerificationMsgKafkaListenerContainerFactory")
    public void receiveDeleteVerificationMsg(DeleteVerificationMsg message) throws IOException, BaseException {
        verificationService.deleteVerification(message);
    }


}
