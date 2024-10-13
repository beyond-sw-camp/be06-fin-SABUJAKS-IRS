package com.example.api.domain.interview_evaluate.model.response;

import com.example.api.domain.interview_evaluate.model.response.InterviewEvaluateReadRes;
import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class InterviewEvaluateReadAllRes {
    private Map<Long, List<InterviewEvaluateReadRes>> interviewEvaluateReadAllResMap;
}
