package com.sabujaks.irs.domain.video_interview.model.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VideoInterviewReadRes {
    private String announcementTitle;
    private String announcementUUID;
    private String interviewScheduleUUID;
    private String interviewDate;
    private String interviewStart;
    private String interviewEnd;
}
