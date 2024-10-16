package com.service.common.dto.feign;

import com.service.common.dto.response.resume.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReadSubmissionResumeRes {
    private List<String> codes;
    private String resumeTitle;
    private LocalDateTime resumedAt;
    private Boolean docPassed;
    private Long seekerIdx;
    private Long announcementIdx;
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
    private List<ReadCustomLetterRes> customLetters; // 자기소개서 n개
    private List<ReadPortfolioRes> portfolios; // 포트폴리오 n개
}
