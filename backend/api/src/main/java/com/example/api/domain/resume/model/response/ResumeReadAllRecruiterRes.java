package com.example.api.domain.resume.model.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResumeReadAllRecruiterRes {
    private Long resumeIdx;
    private String seekerName;
    private Boolean resumeResult;
    private Boolean interviewOneResult;
    private Boolean interviewTwoResult;
    private Boolean finalResult;
    private String resumeTitle;
    private LocalDateTime resumedAt;
}
