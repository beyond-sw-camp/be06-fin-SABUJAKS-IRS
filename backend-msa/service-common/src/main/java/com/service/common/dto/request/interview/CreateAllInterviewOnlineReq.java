package com.service.common.dto.request.interview;

import lombok.*;

@Getter
@Builder
public class CreateAllInterviewOnlineReq {
    private String announcementUuid;
    private Long announcementIdx;
}
