package com.sabujaks.irs.domain.video_interview.mdoel.request;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VideoInterviewRoomCreateReq {
    private String announceUUID;
    private Map<String, Object> params;
    // params => {customSessionId: 세션 이름}
}
