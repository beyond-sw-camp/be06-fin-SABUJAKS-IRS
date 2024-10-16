package com.service.common.dto.request.interview;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class CreateAllInterviewReScheduleReq {
    private Long interviewScheduleIdx;
    private List<Long> seekerList;
    private Long reScheduleIdx;
}
