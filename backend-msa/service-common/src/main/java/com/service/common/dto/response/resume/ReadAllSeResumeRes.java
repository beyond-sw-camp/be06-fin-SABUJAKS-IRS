package com.service.common.dto.response.resume;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReadAllSeResumeRes {
    private Long resumeIdx;
    private String resumeTitle;
    private LocalDateTime resumedAt;
    private Long announcementIdx;
    private String announcementTitle;
    private String announcementStart;
    private String announcementEnd;
    private String companyName;
    private Boolean resumeResult;
    private Boolean interviewOneResult;
    private Boolean interviewTwoResult;
    private Boolean finalResult;
}
