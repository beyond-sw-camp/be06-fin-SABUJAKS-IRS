package com.service.common.dto.request.interview;

import lombok.*;

import java.util.Map;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReadInterviewOlineTokenReq {
    private String announceUUID;
    private String videoInterviewUUID;
    private Map<String, Object> params;
}
