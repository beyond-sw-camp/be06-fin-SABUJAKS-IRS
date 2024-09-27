package com.sabujaks.irs.domain.resume.model.response;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResumeReadAllRecruiterRes {
    private Long resumeIdx;
    private String seekerName;
    private Boolean docPassed;
    private String resumeTitle;
    private LocalDateTime resumedAt;
}
