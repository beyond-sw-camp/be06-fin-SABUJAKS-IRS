package com.example.api.domain.alarm.model.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class AlarmRes {

    private Long idx;
    private String type;
    private Boolean status;
    private String message;
    private String url;
    private LocalDateTime createdAt;
    private Long interviewScheduleIdx;
}
