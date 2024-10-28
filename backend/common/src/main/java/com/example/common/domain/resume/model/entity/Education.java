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
public class Education {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(nullable = false, length = 10)
    private String schoolDiv;

    @Column(nullable = false, length = 20)
    private String schoolName;

    private String enteredAt;

    private String graduatedAt;

    private String graduationStatus;

    @Column(length = 30)
    private String majorName;

    private Double grade;

    private Double totalGrade;

    private Boolean transfer;

    @Column(length = 20)
    private String majorType;

    @Column(length = 30)
    private String otherMajor;

    @Column(columnDefinition = "TEXT")
    private String graduationWork;

    @Column(length = 10)
    private String degree;

    @Column(nullable = false)
    @Builder.Default
    private Boolean qualificationExam = false;

    private String passedAt;

    // 지원정보 테이블과 n:1
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_info_idx")
    private ResumeInfo resumeInfo;
}
