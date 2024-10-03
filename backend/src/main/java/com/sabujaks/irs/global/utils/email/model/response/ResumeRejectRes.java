package com.sabujaks.irs.global.utils.email.model.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResumeRejectRes {
    private Long seekerIdx;
    private String seekerName;
    private String seekerEmail;
    private String companyName;
    private String announcementTitle;
}
