package com.example.api.domain.resume.model.request;

import com.example.api.domain.resume.model.request.InternsActivityCreateReq;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResumeCreateReq {
    private List<String> codes;
    private PersonalInfoCreateReq personalInfo; // 인적사항 1개
    private PreferentialEmpInfoCreateReq preferentialEmp; // 취업우대·병역 1개
    private List<EducationCreateReq> educations; // 학력 n개
    private List<PersonalHistoryCreateReq> personalHistories; // 경력 n개
    private List<InternsActivityCreateReq> internsActivities; // 인턴·대외활동 n개
    private List<StudyingAbroadCreateReq> studyingAbroads; // 해외경험 n개
    private List<LanguageCreateReq> languages; // 어학 n개
    private List<CertificationCreateReq> certifications; // 자격증 n개
    private List<TrainingCreateReq> trainings; // 교육이수 n개
    private List<AwardCreateReq> awards; // 수상 n개
    private List<CustomLetterCreateReq> customLetters; // 자기소개서 n개
    private List<PortfolioCreateReq> portfolios; // 포트폴리오 n개
}