package com.sabujaks.irs.domain.interview_schedule.model.response;

import com.sabujaks.irs.domain.auth.model.response.SeekerInfoGetRes;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class InterviewScheduleRes {
    private Long idx;
    private List<SeekerInfoGetRes> seekerList;
    private List<String> estimatorList;
    private Boolean isOnline;
    private String interviewDate;
    private String interviewStart;
    private String interviewEnd;
    private String uuid;
    private Long teamIdx;
}
