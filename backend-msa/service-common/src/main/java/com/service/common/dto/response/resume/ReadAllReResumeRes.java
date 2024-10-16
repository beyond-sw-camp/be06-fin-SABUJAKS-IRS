package com.service.common.dto.response.resume;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReadAllReResumeRes {
    private Long resumeIdx;
    private String seekerName;
    private Long seekerIdx;
    private Boolean resumeResult;
    private Boolean interviewOneResult;
    private Boolean interviewTwoResult;
    private Boolean finalResult;
    private String resumeTitle;
    private LocalDateTime resumedAt;
}
