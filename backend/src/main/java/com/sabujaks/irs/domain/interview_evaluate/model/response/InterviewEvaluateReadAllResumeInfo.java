package com.sabujaks.irs.domain.interview_evaluate.model.response;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InterviewEvaluateReadAllResumeInfo {
    private Map<Long, InterviewEvaluateReadResumeInfoRes> interviewEvaluateReadResumeInfoResMap;
}
