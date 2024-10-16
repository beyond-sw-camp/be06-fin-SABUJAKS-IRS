package com.service.common.dto.response.interview;

import com.service.common.dto.feign.ReadSeekerRes;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ReadInterviewReScheduleRes {
    private Long idx;
    private String interviewStart;
    private String interviewEnd;
    private ReadInterviewScheduleRes readInterviewScheduleRes;
    private ReadSeekerRes readSeekerRes;
    private Boolean status;
}
