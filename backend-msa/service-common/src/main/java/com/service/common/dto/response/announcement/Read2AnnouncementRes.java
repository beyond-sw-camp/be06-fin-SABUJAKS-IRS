package com.service.common.dto.response.announcement;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
// TODO: REFCATIROIMNG
public class Read2AnnouncementRes {
    private Long announcementIdx;
    private String announcementTitle;
    private String announcementStart;
    private String announcementEnd;
    private String careerBase;

}
