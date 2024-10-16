package com.service.common.dto.request.interview;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreateInterviewReScheduleReq {
    private String interviewStart;
    private String interviewEnd;
    private Long interviewScheduleIdx;
}
