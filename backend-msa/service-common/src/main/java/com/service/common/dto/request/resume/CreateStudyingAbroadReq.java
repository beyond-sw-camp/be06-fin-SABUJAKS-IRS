package com.service.common.dto.request.resume;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateStudyingAbroadReq {
    private String countryName;
    private String startAt;
    private String endAt;
    private String contents;
}
