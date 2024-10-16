package com.service.common.dto.response.resume;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReadStudyingAbroadRes {
    private String countryName;
    private String startAt;
    private String endAt;
    private String contents;
}
