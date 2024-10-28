package com.example.api.domain.video_interview.model.request;

import lombok.*;

import java.util.Map;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VideoInterviewTokenGetReq {
    private String announceUUID;
    private String videoInterviewUUID;
    private Map<String, Object> params;
}
