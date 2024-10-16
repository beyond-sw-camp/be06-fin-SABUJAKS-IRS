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

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
// 특혜 우대사항 엔티티
public class PreferentialEmp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(nullable = false)
    private Boolean veterans; // 보훈대상 여부

    @Column(nullable = false)
    private Boolean protection; // 취업보호대상 여부

    @Column(nullable = false)
    private Boolean subsidy; // 고용지원금대상 여부

    @Column(nullable = false)
    private Boolean disability; // 장애 여부

    @Column(length = 20)
    private String disabilityDegree; // 장애 등급

    @Column(nullable = false)
    private Boolean military; // 병역 여부

    @Column(length = 20)
    private String militaryClass; // 병역

    private String militaryStart; // 입대일

    private String militaryEnd; // 제대일

    @Column(length = 20)
    private String militaryType; // 군별

    @Column(length = 20)
    private String militaryRank; // 제대 계급

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    // 지원정보 테이블과 1:1
    @OneToOne(fetch = FetchType.LAZY)
    private Resume resume;
}
