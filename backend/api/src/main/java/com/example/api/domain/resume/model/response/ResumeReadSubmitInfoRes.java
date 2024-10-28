package com.example.api.domain.resume.model.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResumeReadSubmitInfoRes {
    private List<String> codes; // 지원서 맞춤 양식 코드
    private Boolean integrated; // 통합 지원서 등록 여부 반환
    private PersonalInfoReadRes personalInfo; // 인적사항 1개
    private PreferentialEmpReadRes preferentialEmp; // 취업우대·병역 1개
    private List<EducationReadRes> educations; // 학력 n개
    private List<PersonalHistoryReadRes> personalHistories; // 경력 n개
    private List<InternsActivityReadRes> internsActivities; // 인턴·대외활동 n개
    private List<StudyingAbroadReadRes> studyingAbroads; // 해외경험 n개
    private List<LanguageReadRes> languages; // 어학 n개
    private List<CertificationReadRes> certifications; // 자격증 n개
    private List<TrainingReadRes> trainings; // 교육이수 n개
    private List<AwardReadRes> awards; // 수상 n개
    private List<CustomLetterFormReadRes> customLetterForms; // 공고등록 step2에서 등록한 자기소개서 문항
    private List<PortfolioReadRes> portfolios; // 포트폴리오 n개
}
