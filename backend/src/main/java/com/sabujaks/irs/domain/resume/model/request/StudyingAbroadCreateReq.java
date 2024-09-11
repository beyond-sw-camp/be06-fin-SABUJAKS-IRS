package com.sabujaks.irs.domain.resume.model.request;

import jakarta.persistence.Column;
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
