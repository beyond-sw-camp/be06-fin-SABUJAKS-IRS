package com.sabujaks.irs.domain.video_interview.mdoel.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VideoInterviewSearchRes {
    private Long idx;
    private String announceUUID;
    private String interviewScheduleUUID;
}
