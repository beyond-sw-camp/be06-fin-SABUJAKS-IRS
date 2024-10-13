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
public class PersonalHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(nullable = false, length = 50)
    private String companyName;

    @Column(length = 20)
    private String deptName;

    @Column(nullable = false)
    private String enteredAt;

    private String quitAt;

    private Boolean empStatus;

    @Column(length = 20)
    private String position;

    @Column(nullable = false, length = 20)
    private String job;

    private Integer salary;

    @Column(columnDefinition = "TEXT")
    private String work;

    // 지원정보 테이블과 n:1
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_info_idx")
    private ResumeInfo resumeInfo;
}
