package com.service.common.dto.request.interview;

import com.service.common.dto.response.interview.ReadInterviewScheduleRes;
import lombok.*;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateInterviewOnlineReq {
    private String announceUUID;
    private Map<String, Object> params;
    private ReadInterviewScheduleRes readInterviewScheduleRes;
}
