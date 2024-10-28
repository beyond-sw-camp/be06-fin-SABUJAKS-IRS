package com.example.api.domain.interview_evaluate.model.response;

import com.example.api.domain.interview_evaluate.model.response.InterviewEvaluateReadResumeInfoRes;
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
