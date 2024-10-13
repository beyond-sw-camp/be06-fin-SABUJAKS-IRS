package com.example.api.domain.interview_schedule.model.request;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class InterviewScheduleReq {
    private List<Long> seekerList;
    private List<String> estimatorList;
    private Boolean isOnline;
    private String interviewDate;
    private String interviewStart;
    private String interviewEnd;
    private String careerBase;
    private Integer interviewNum;
    private Long announcementIdx;
    private Long teamIdx;
}
