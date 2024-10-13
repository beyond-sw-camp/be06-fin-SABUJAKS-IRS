package com.example.api.domain.resume.model.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudyingAbroadCreateReq {
    private String countryName;
    private String startAt;
    private String endAt;
    private String contents;
}
