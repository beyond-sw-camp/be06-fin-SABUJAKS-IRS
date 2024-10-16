package com.sabujaks.irs.domain.total_process.model.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TotalProcessReadAllRes {
    private Long totalProcessIdx;
    private Boolean resumeResult;
    private Boolean interviewOneResult;
    private Boolean interviewTwoResult;
    private Boolean finalResult;
    private String seekerName;
}
