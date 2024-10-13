package com.example.api.domain.resume.model.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudyingAbroadReadRes {
    private String countryName;
    private String startAt;
    private String endAt;
    private String contents;
}
