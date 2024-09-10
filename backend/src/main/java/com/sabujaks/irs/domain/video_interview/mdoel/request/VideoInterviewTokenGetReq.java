package com.sabujaks.irs.domain.video_interview.mdoel.request;

import lombok.*;

import java.util.Map;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VideoInterviewTokenGetReq {
    private String announceUUID;
    private Map<String, Object> params;
    private String videoInterviewRoomUUID;
}
