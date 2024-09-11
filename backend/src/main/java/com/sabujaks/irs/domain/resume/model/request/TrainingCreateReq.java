package com.sabujaks.irs.domain.resume.model.request;

import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainingCreateReq {
    private String trainingName;
    private String organization;
    private String startAt;
    private String endAt;
    private String contents;
}
