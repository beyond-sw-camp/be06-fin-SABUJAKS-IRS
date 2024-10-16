package com.service.common.dto.response.interview;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReadInterviewOnlineRes {
    private String announcementTitle;
    private String announcementUUID;
    private String interviewScheduleUUID;
    private String interviewDate;
    private String interviewStart;
    private String interviewEnd;
}
