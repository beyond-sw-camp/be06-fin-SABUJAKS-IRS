package com.sabujaks.irs.domain.interview_schedule.model.request;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class InterviewScheduleReq {

    private List<Long> seekerList;
    private List<String> interviewerList;
    private String isOnline;
    private String interviewDate;
    private String interviewStart;
    private String interviewEnd;
    private String careerBase;
    private Long teamIdx;
}
