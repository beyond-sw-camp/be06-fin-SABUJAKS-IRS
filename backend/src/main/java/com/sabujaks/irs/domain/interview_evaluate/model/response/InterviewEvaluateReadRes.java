package com.sabujaks.irs.domain.interview_evaluate.model.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InterviewEvaluateReadRes {
    private String seekerName;
    private Integer totalScore;
    private String comments;
    private String estimatorEmail;
}
