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
// 자기소개서
public class CustomLetter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Integer charNum;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String contents;

    // 지원정보 테이블과 n:1
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_info_idx")
    private ResumeInfo resumeInfo;
}
