package com.service.interview.communication;

import com.service.common.dto.kafka.CreateEstimatorMsg;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InterviewKafkaProducer {
    private final KafkaTemplate<String, CreateEstimatorMsg> createEstimatorMsgKafkaTemplate;

    public void createEstimatorMsg(CreateEstimatorMsg message) { createEstimatorMsgKafkaTemplate.send("CREATE_ESTIMATOR", message); }

}
