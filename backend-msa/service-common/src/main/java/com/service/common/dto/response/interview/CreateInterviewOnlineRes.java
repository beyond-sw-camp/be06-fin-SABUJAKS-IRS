package com.service.common.dto.response.interview;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateInterviewOnlineRes {
    private Long idx;
    private ReadInterviewScheduleRes interviewScheduleRes;
    private String announcementUuid;
}