package com.service.resume.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
// 맞춤 지원정보 엔티티 (어떤 정보들이 지원정보에 연결되어있는지 저장)
public class CustomResume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private Boolean resume001; // 학력
    private Boolean resume002; // 경력
    private Boolean resume003; // 인턴·대외활동
    private Boolean resume004; // 교육이수
    private Boolean resume005; // 자격증
    private Boolean resume006; // 수상
    private Boolean resume007; // 해외경험
    private Boolean resume008; // 어학
    private Boolean resume009; // 포트폴리오
    private Boolean resume010; // 취업우대&병역
    private Boolean resume011; // 자기소개서

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    // 지원정보 테이블과 n:1
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_idx")
    private Resume resume;
}
