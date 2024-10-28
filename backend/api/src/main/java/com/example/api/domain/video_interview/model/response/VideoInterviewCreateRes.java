package com.example.api.domain.video_interview.model.response;

import com.example.api.domain.interview_schedule.model.response.InterviewScheduleRes;
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