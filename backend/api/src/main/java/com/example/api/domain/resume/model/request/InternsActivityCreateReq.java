package com.example.api.domain.resume.model.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InternsActivityCreateReq {
    private String activityDiv;
    private String organization;
    private String startAt;
    private String endAt;
    private String contents;
}
