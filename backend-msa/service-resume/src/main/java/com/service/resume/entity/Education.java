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
// 교육 엔티티
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

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    // 지원정보 테이블과 n:1
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_idx")
    private Resume resume;
}
