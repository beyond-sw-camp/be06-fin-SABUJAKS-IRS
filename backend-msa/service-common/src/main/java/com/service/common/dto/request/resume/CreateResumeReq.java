package com.service.common.dto.request.resume;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateResumeReq {
    private List<String> codes;
    private CreatePersonalInfoReq personalInfo; // 인적사항 1개
    private CreatePreferentialEmpInfoReq preferentialEmp; // 취업우대·병역 1개
    private List<CreateEducationReq> educations; // 학력 n개
    private List<CreatePersonalHistoryReq> personalHistories; // 경력 n개
    private List<CreateInternsActivityReq> internsActivities; // 인턴·대외활동 n개
    private List<CreateStudyingAbroadReq> studyingAbroads; // 해외경험 n개
    private List<CreateLanguageReq> languages; // 어학 n개
    private List<CreateCertificationReq> certifications; // 자격증 n개
    private List<CreateTrainingReq> trainings; // 교육이수 n개
    private List<CreateAwardReq> awards; // 수상 n개
    private List<CreateCustomLetterReq> customLetters; // 자기소개서 n개
    private List<CreatePortfolioReq> portfolios; // 포트폴리오 n개
}