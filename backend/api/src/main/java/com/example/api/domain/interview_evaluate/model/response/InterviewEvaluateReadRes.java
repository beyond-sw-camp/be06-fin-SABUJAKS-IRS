package com.example.api.domain.interview_evaluate.model.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InterviewEvaluateReadRes {
    private String seekerName;
    private String seekerEmail;
    private Integer totalScore;
    private String comments;
    private String estimatorName;
    private String estimatorEmail;
    private InterviewEvaluateFormReadRes interviewEvaluateFormReadRes;
    private InterviewEvaluateResultReadRes interviewEvaluateResultReadRes;
}
