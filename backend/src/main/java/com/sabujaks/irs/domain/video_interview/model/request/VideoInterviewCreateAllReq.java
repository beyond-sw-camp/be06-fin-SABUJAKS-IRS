package com.sabujaks.irs.domain.video_interview.model.request;

import lombok.*;

@Getter
@Builder
public class VideoInterviewCreateAllReq {
    private String announcementUuid;
    private Long announcementIdx;
}
