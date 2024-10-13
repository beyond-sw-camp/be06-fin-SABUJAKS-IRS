package com.example.api.domain.interview_schedule.model.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TimeInfoRes {

    private String interviewStart;
    private String interviewEnd;
}
