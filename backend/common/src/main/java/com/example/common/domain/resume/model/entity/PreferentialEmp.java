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
public class PreferentialEmp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    // 보훈대상 여부
    @Column(nullable = false)
    private Boolean veterans;
    // 취업보호대상 여부
    @Column(nullable = false)
    private Boolean protection;
    // 고용지원금대상 여부
    @Column(nullable = false)
    private Boolean subsidy;

    // 장애 여부
    @Column(nullable = false)
    private Boolean disability;
    // 장애 등급
    @Column(length = 20)
    private String disabilityDegree;

    // 병역 여부
    @Column(nullable = false)
    private Boolean military;
    // 병역
    @Column(length = 20)
    private String militaryClass;
    // 입대일
    private String militaryStart;
    // 제대일
    private String militaryEnd;
    // 군별
    @Column(length = 20)
    private String militaryType;
    // 제대 계급
    @Column(length = 20)
    private String militaryRank;

    // 지원정보 테이블과 1:1
    @OneToOne(fetch = FetchType.LAZY)
    private ResumeInfo resumeInfo;
}
