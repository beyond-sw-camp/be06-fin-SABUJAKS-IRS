package com.service.common.dto.response.interview;

import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ReadAllInterviewEvaluateRes {
    private Map<Long, List<ReadInterviewEvaluateRes>> interviewEvaluateReadAllResMap;
}
