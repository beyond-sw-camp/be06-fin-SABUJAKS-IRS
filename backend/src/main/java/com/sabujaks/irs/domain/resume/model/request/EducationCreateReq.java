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
    private Boolean highLess;
    private String schoolDiv;
    private String schoolName;
    private String enteredAt;
    private String graduatedAt;
    private String graduationStatus;
    private String majorName;
    private Double grade;
    private Double totalGrade;
    private Boolean transfer;
    private String majorType;
    private String otherMajor;
    private String graduationWork;
    private String degree;
    private Boolean qualificationExam;
    private String passedAt;
}
