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
public class PersonalHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(nullable = false, length = 50)
    private String company_name;

    @Column(length = 20)
    private String dept_name;

    @Column(nullable = false)
    private String entered_at;

    private String quit_at;

    private Boolean emp_status;

    @Column(length = 20)
    private String position;

    @Column(nullable = false, length = 20)
    private String job;

    private Integer salary;

    private String work;

    // 지원정보 테이블과 n:1
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_info_idx")
    private ResumeInfo resumeInfo;
}
