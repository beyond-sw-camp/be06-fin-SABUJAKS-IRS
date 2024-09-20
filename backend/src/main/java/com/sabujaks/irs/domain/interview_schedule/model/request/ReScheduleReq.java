package com.sabujaks.irs.domain.interview_schedule.model.request;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ReScheduleReq {
    private String interviewStart;
    private String interviewEnd;
    private Long interviewScheduleIdx;
}
