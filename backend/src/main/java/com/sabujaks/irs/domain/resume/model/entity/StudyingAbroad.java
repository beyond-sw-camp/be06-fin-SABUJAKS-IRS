package com.sabujaks.irs.domain.resume.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
// 해외경험
public class StudyingAbroad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(nullable = false, length = 30)
    private String countryName;

    @Column(length = 10)
    private String startAt;

    @Column(length = 10)
    private String endAt;

    @Column(columnDefinition = "TEXT")
    private String contents;

    // 지원정보 테이블과 n:1
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_info_idx")
    private ResumeInfo resumeInfo;
}
