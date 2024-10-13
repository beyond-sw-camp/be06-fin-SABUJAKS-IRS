package com.example.api.domain.resume.model.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AwardReadRes {
    private String awardName;
    private String contents;
    private String organization;
    private String year;
}
