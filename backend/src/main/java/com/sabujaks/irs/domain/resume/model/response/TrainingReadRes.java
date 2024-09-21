package com.sabujaks.irs.domain.resume.model.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainingReadRes {
    private String trainingName;
    private String organization;
    private String startAt;
    private String endAt;
    private String contents;
}
