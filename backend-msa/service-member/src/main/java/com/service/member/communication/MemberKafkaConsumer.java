package com.service.member.communication;

import com.service.common.dto.kafka.CreateEstimatorMsg;
import com.service.common.dto.kafka.CreatedEmailVerificationMsg;
import com.service.common.base.BaseException;
import com.service.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class MemberKafkaConsumer {

    private final MemberService memberService;

    @KafkaListener(topics = "CREATED_EMAIL_VERIFICATION", groupId = "member", containerFactory = "createdEmailVerificationMsgKafkaListenerContainerFactory")
    public void receiveCreatedEmailVerificationMsg(CreatedEmailVerificationMsg message) throws IOException, BaseException {
        memberService.activeMember(message);
    }

    @KafkaListener(topics = "CREATE_ESTIMATOR", groupId = "member", containerFactory = "createEstimatorMsgKafkaListenerContainerFactory")
    public void receiveCreateEstimatorMsg(CreateEstimatorMsg message) throws IOException, BaseException {
        memberService.createEstimator(message);
    }
}
