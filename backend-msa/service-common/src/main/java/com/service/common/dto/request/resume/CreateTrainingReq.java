package com.service.common.dto.request.resume;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateTrainingReq {
    private String trainingName;
    private String organization;
    private String startAt;
    private String endAt;
    private String contents;
}
