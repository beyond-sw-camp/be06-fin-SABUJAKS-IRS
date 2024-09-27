package com.sabujaks.irs.domain.announcement.model.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnnouncementReadAllRes3 {
    private Long announcementIdx;
    private String announcementTitle;
    private String announcementStart;
    private String announcementEnd;
    private String careerBase;
    private Long seekerNum;
}
