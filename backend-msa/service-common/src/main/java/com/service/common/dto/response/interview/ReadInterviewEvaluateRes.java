package com.service.common.dto.response.interview;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReadInterviewEvaluateRes {
    private String seekerName;
    private String seekerEmail;
    private Integer totalScore;
    private String comments;
    private String estimatorName;
    private String estimatorEmail;
    private ReadInterviewEvaluateFormRes readInterviewEvaluateFormRes;
    private ReadInterviewEvaluateResultRes readInterviewEvaluateResultRes;
}
