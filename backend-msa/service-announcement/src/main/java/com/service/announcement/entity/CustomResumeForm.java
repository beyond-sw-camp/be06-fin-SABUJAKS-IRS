package com.service.announcement.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
// 지원서 맞춤 양식 엔티티
public class CustomResumeForm {
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "announcement_idx")
    private Announcement announcement;
}