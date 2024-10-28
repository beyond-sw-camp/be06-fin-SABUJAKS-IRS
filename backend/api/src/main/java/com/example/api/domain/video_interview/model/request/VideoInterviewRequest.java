package com.example.api.domain.video_interview.model.request;

import lombok.Data;

@Data
public class VideoInterviewRequest {

    private String announceUUID;
    private String videoInterviewUUID;
    private Params params;

    @Data
    public static class Params {
        private String customSessionId;
    }
}
