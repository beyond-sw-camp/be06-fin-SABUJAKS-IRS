package com.sabujaks.irs.domain.interview_schedule.model.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class InterviewScheduleRes {

    private Long idx;
    private List<String> seekerList;
    private List<String> interviewerList;
    private Boolean isOnline;
    private String interviewDate;
    private String interviewStart;
    private String interviewEnd;
}
