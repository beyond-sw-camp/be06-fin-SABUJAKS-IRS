package com.example.common.domain.resume.model.entity;

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
// 어학
public class Language {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(nullable = false, length = 30)
    private String testDiv;

    @Column(nullable = false, length = 30)
    private String languageName;

    @Column(length = 30)
    private String conversationLevel;

    @Column(length = 30)
    private String officialTest;

    @Column(length = 10)
    private String score;

    @Column(length = 10)
    private String selectScore;

    @Column(length = 10)
    private String takingAt;

    // 지원정보 테이블과 n:1
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_info_idx")
    private ResumeInfo resumeInfo;
}
