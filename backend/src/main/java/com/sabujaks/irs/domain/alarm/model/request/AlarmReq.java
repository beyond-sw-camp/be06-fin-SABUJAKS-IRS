package com.sabujaks.irs.domain.alarm.model.request;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class AlarmReq {

    private String type;
    private Boolean status;
    private String message;
    private String url;
    private LocalDateTime createdAt;
}
