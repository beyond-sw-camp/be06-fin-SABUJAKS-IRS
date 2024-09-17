package com.sabujaks.irs.domain.video_interview.model.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VideoInterviewSearchRes {
    private String announceUUID;
    private String videoInterviewUUID;
    private String interviewDate;
    private String interviewStart;
    private String interviewEnd;
}
