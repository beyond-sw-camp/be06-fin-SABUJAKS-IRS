package com.sabujaks.irs.domain.resume.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Education {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(nullable = false)
    @Builder.Default
    private Boolean high_less = false;

    @Column(nullable = false, length = 10)
    private String school_div;

    @Column(nullable = false, length = 20)
    private String school_name;

    private String entered_at;

    private String graduated_at;

    private String graduation_status;

    @Column(length = 30)
    private String major_name;

    private Double grade;

    private Double total_grade;

    private Boolean transfer;

    @Column(length = 20)
    private String major_type;

    @Column(length = 30)
    private String other_major;

    @Column(columnDefinition = "TEXT")
    private String graduation_work;

    @Column(length = 10)
    private String degree;

    @Column(nullable = false)
    @Builder.Default
    private Boolean qualification_exam = false;

    private String passed_at;

    // 지원정보 테이블과 n:1
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_info_idx")
    private ResumeInfo resumeInfo;
}
