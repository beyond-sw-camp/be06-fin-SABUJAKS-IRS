package com.service.common.dto.response.interview;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReadAvailableTimeRes {
    private String interviewStart;
    private String interviewEnd;
}
