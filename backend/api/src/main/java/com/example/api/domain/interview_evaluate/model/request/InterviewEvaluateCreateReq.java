package com.example.api.domain.interview_evaluate.model.request;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InterviewEvaluateCreateReq {
    private String userEmail;
    private Map<Integer, Integer> scores; // 질문 번호와 점수의 맵
    private Integer totalScore;
    private Boolean status;
    private String comments;
    private String announcementUUID;
    private String videoInterviewUUID;
}
