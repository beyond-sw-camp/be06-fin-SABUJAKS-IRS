package com.sabujaks.irs.domain.video_interview.model.request;

import com.sabujaks.irs.domain.interview_schedule.model.response.InterviewScheduleRes;
import lombok.*;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VideoInterviewCreateReq {
    private String announceUUID;
    private Map<String, Object> params;
    private InterviewScheduleRes interviewScheduleInfo;
}
