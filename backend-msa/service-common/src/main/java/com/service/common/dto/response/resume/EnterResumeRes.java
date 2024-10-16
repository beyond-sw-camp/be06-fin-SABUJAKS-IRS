package com.service.common.dto.response.resume;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnterResumeRes {
    private List<String> codes; // 지원서 맞춤 양식 코드
    private Boolean integrated; // 통합 지원서 등록 여부 반환
    private ReadPersonalInfoRes personalInfo; // 인적사항 1개
    private ReadPreferentialEmpRes preferentialEmp; // 취업우대·병역 1개
    private List<ReadEducationRes> educations; // 학력 n개
    private List<ReadPersonalHistoryRes> personalHistories; // 경력 n개
    private List<ReadInternsActivityRes> internsActivities; // 인턴·대외활동 n개
    private List<ReadStudyingAbroadRes> studyingAbroads; // 해외경험 n개
    private List<ReadLanguageRes> languages; // 어학 n개
    private List<ReadCertificationRes> certifications; // 자격증 n개
    private List<ReadTrainingRes> trainings; // 교육이수 n개
    private List<ReadAwardRes> awards; // 수상 n개
    private List<ReadCustomLetterFormRes> customLetterForms; // 공고등록 step2에서 등록한 자기소개서 문항
    private List<ReadPortfolioRes> portfolios; // 포트폴리오 n개
}
