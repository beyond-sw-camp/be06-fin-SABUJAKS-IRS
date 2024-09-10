package com.sabujaks.irs.domain.resume.model.request;

import com.sabujaks.irs.domain.resume.model.entity.PersonalHistory;
import com.sabujaks.irs.domain.resume.model.entity.PersonalInfo;
import lombok.*;

import java.lang.reflect.Array;
import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResumeCreateReq {
    private ArrayList<String> codes;
    private PersonalInfoCreateReq personalInfo; // 인적사항 1개
    private PreferentialEmpInfoCreateReq preferentialEmp; // 취업우대&병역 1개
    private ArrayList<EducationCreateReq> educations; // 학력 n개
    private ArrayList<PersonalHistoryCreateReq> personalHistories; // 경력 n개
}