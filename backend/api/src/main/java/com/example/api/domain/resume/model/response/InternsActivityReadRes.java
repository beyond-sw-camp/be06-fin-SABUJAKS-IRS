package com.example.api.domain.resume.model.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InternsActivityReadRes {
    private String activityDiv;
    private String organization;
    private String startAt;
    private String endAt;
    private String contents;
}
