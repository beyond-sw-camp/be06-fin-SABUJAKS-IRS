package com.sabujaks.irs.domain.resume.model.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResumeUpdateDocPassedRes {
    private Long resumeIdx;
    private Boolean docPassed;
}
