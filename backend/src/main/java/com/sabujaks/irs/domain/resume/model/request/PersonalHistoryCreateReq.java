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
    private String company_name;
    private String dept_name;
    private String entered_at;
    private String quit_at;
    private Boolean emp_status;
    private String position;
    private String job;
    private Integer salary;
    private String work;
}
