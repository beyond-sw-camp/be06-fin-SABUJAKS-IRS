package com.service.common.dto.response.resume;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReadTrainingRes {
    private String trainingName;
    private String organization;
    private String startAt;
    private String endAt;
    private String contents;
}
