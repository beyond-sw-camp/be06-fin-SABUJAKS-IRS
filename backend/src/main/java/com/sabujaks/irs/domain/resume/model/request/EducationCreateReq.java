package com.sabujaks.irs.domain.resume.model.request;

import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EducationCreateReq {
    private Boolean high_less;
    private String school_div;
    private String school_name;
    private String entered_at;
    private String graduated_at;
    private String graduation_status;
    private String major_name;
    private Double grade;
    private Double total_grade;
    private Boolean transfer;
    private String major_type;
    private String other_major;
    private String graduation_work;
    private String degree;
    private Boolean qualification_exam;
    private String passed_at;
}
