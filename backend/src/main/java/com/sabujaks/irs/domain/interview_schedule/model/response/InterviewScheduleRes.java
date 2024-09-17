package com.sabujaks.irs.domain.interview_schedule.model.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class InterviewScheduleRes {
    private Long idx;
    private List<Long> seekerList;
    private List<String> estimatorList;
    private Boolean isOnline;
    private String interviewDate;
    private String interviewStart;
    private String interviewEnd;
}
