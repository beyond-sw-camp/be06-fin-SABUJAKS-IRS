package com.sabujaks.irs.domain.interview_schedule.model.request;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class InterviewScheduleUpdateReq {
    private Long interviewScheduleIdx;
    private List<Long> seekerList;
}
