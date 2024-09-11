package com.sabujaks.irs.domain.resume.model.request;

import com.sabujaks.irs.domain.resume.model.entity.StudyingAbroad;
import lombok.*;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResumeCreateReq {
    private ArrayList<String> codes;
    private PersonalInfoCreateReq personalInfo; // 인적사항 1개
    private PreferentialEmpInfoCreateReq preferentialEmp; // 취업우대·병역 1개
    private ArrayList<EducationCreateReq> educations; // 학력 n개
    private ArrayList<PersonalHistoryCreateReq> personalHistories; // 경력 n개
    private ArrayList<InternsActivityCreateReq> internsActivities; // 인턴·대외활동 n개
    private ArrayList<StudyingAbroadCreateReq> studyingAbroads; // 해외경험 n개
    private ArrayList<LanguageCreateReq> languages; // 어학 n개
    private ArrayList<CertificationCreateReq> certifications; // 자격증 n개
    private ArrayList<TrainingCreateReq> trainings; // 교육이수 n개
    private ArrayList<AwardCreateReq> awards; // 수상 n개
}