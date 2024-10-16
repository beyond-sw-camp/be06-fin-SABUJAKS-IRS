package com.service.resume.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
// 내가 쓴 지원서 엔티티(통합, 공고에 지원한 지원서)
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(nullable = false)
    private Boolean integrated;

    @Column(nullable = false)
    private Long seekerIdx; // msa_member

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    // 학력 테이블과 1:n
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "resume")
    private List<Education> educationList;

    // 경력 테이블과 1:n
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "resume")
    private List<PersonalHistory> personalHistoryList;

    // 인턴·대외활동 테이블과 1:n
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "resume")
    private List<InternsActivity> internsActivityList;

    // 해외경험 테이블과 1:n
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "resume")
    private List<StudyingAbroad> studyingAbroadList;

    // 어학 테이블과 1:n
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "resume")
    private List<Language> languageList;

    // 자격증 테이블과 1:n
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "resume")
    private List<Certification> certificationList;

    // 교육이수 테이블과 1:n
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "resume")
    private List<Training> trainingList;

    // 수상 테이블과 1:n
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "resume")
    private List<Award> awardList;

    // 자기소개서 테이블과 1:n
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "resume")
    private List<CustomLetter> customLetterList;

    // 포트폴리오 테이블과 1:n
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "resume")
    private List<Portfolio> portfolioList;

    // 공고지원서 테이블과 1:n
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "resume")
    private List<SubmissionResume> submissionResumeList;

    // 맞춤 지원정보 테이블과 1:n
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "resume")
    private List<CustomResume> customResumeList;

//    // 회원(지원자) 테이블과 n:1 -> seekerIdx
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "seeker_idx")
//    private Seeker seeker;
}
