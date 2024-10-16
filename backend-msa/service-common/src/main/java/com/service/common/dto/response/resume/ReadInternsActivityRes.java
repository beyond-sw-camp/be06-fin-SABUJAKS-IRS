package com.service.common.dto.response.resume;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReadInternsActivityRes {
    private String activityDiv;
    private String organization;
    private String startAt;
    private String endAt;
    private String contents;
}
