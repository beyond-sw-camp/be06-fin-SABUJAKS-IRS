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
// 인턴·대외활동
public class InternsActivity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(nullable = false, length = 30)
    private String activityDiv;

    @Column(nullable = false, length = 50)
    private String organization;

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
