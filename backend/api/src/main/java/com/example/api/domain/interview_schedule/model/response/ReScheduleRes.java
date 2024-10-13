package com.example.api.domain.interview_schedule.model.response;

import com.example.api.domain.auth.model.entity.Seeker;
import com.example.api.domain.auth.model.response.SeekerInfoGetRes;
import com.example.api.domain.interview_schedule.model.entity.InterviewSchedule;
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
