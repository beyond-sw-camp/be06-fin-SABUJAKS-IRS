package com.example.api.domain.resume.model.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AwardCreateReq {
    private String awardName;
    private String contents;
    private String organization;
    private String year;
}
