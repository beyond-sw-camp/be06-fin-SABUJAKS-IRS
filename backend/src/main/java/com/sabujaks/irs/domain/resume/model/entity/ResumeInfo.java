package com.sabujaks.irs.domain.resume.model.entity;

import com.sabujaks.irs.domain.auth.model.entity.Seeker;
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

    // 회원 테이블과 n:1
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seeker_idx")
    private Seeker seeker;

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
}
