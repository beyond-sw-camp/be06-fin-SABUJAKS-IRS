package com.sabujaks.irs.domain.video_interview.model.response;

import com.sabujaks.irs.domain.interview_schedule.model.response.InterviewScheduleRes;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VideoInterviewCreateRes {
    private Long idx;
    private InterviewScheduleRes interviewScheduleRes;
    private String announcementUuid;
}