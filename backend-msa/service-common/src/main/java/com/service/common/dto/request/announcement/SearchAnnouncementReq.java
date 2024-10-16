package com.service.common.dto.request.announcement;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchAnnouncementReq {
    private Integer page;
    private Integer size;
    private String keyword;
    private String careerBase;
    private String jobCategory;
    private String region;
    private String sort;
}
