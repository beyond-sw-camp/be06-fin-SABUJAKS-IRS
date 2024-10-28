package com.example.api.domain.total_process.model.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TotalProcessCreateReq {
    private Long announcementIdx;
    private Long seekerIdx;
    private Integer interviewNum;
    private Integer isPass;
}
