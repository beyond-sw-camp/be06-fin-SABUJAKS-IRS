package com.service.common.dto.response.interview;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ReadAllInterviewEvaluateFormRes {
    private Map<Long, ReadInterviewEvaluateFormRes> interviewEvaluateFormReadResMap;
}
