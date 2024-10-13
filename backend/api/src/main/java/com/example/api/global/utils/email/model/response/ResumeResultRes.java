package com.example.api.global.utils.email.model.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResumeResultRes {
    private Long seekerIdx;
    private String seekerName;
    private String seekerEmail;
    private String companyName;
    private String announcementTitle;
    private Long resumeIdx;
    private Long announcementIdx;
}
