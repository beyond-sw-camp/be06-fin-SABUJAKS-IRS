package com.sabujaks.irs.domain.resume.model.request;

import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDate;

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
