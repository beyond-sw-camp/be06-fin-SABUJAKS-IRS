package com.sabujaks.irs.domain.alarm.model.response;

import com.sabujaks.irs.domain.auth.model.entity.Seeker;
import com.sabujaks.irs.domain.interview_schedule.model.entity.InterviewSchedule;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class AlarmRes {

    private Long idx;
    private String type;
    private Boolean status;
    private String message;
    private String url;
    private LocalDateTime createdAt;
    private Long interviewScheduleIdx;
}
