package com.example.api.domain.resume.model.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonalHistoryCreateReq {
    private String companyName;
    private String deptName;
    private String enteredAt;
    private String quitAt;
    private Boolean empStatus;
    private String position;
    private String job;
    private Integer salary;
    private String work;
}
