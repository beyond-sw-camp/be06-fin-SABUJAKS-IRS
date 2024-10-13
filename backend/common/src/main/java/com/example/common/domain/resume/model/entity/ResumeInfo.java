package com.example.common.domain.resume.model.entity;

import com.example.common.domain.auth.model.entity.Seeker;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResumeInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(nullable = false)
    private Boolean integrated;


    // 학력 테이블과 1:n
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "resumeInfo")
    private List<Education> educationList;

    // 경력 테이블과 1:n
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "resumeInfo")
    private List<PersonalHistory> personalHistoryList;

    // 인턴·대외활동 테이블과 1:n
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "resumeInfo")
    private List<InternsActivity> internsActivityList;

    // 해외경험 테이블과 1:n
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "resumeInfo")
    private List<StudyingAbroad> studyingAbroadList;

    // 어학 테이블과 1:n
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "resumeInfo")
    private List<Language> languageList;

    // 자격증 테이블과 1:n
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "resumeInfo")
    private List<Certification> certificationList;

    // 교육이수 테이블과 1:n
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "resumeInfo")
    private List<Training> trainingList;

    // 수상 테이블과 1:n
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "resumeInfo")
    private List<Award> awardList;

    // 자기소개서 테이블과 1:n
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "resumeInfo")
    private List<CustomLetter> customLetterList;

    // 포트폴리오 테이블과 1:n
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "resumeInfo")
    private List<Portfolio> portfolioList;

    // 공고지원서 테이블과 1:n
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "resumeInfo")
    private List<Resume> resumeList;

    // 맞춤 지원정보 테이블과 1:n
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "resumeInfo")
    private List<CustomResumeInfo> customResumeInfoList;

    // 회원 테이블과 n:1
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seeker_idx")
    private Seeker seeker;
}
