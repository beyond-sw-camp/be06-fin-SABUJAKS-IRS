package com.sabujaks.irs.domain.interview_schedule.model.response;

import com.sabujaks.irs.domain.auth.model.entity.Seeker;
import com.sabujaks.irs.domain.auth.model.response.SeekerInfoGetRes;
import com.sabujaks.irs.domain.interview_schedule.model.entity.InterviewSchedule;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ReScheduleRes {
    private Long idx;
    private String interviewStart;
    private String interviewEnd;
    private InterviewScheduleRes interviewScheduleRes;
    private SeekerInfoGetRes seekerInfoGetRes;
    private Boolean status;
}
