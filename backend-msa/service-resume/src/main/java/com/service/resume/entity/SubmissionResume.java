package com.service.resume.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
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
// 공고지원서 (각 공고에 지원한 지원서 정보)
public class SubmissionResume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(nullable = false)
    private Long seekerIdx; // msa_member

    // TODO: MSA 공고 서비스
//    @Column(nullable = false)
    private Long announcementIdx; // msa_announcement
    @Column(nullable = false, length = 100)
    private String resumeTitle;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime resumedAt;

    // 지원정보 테이블과 n:1
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_idx")
    private Resume resume;


//    // 알람 테이블과 1:n
//    @OneToMany(mappedBy = "resume")
//    private List<Alarm> alarm;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

//    // 회원(지원자) 테이블과 n:1 -> seekerIdx
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "seeker_idx")
//    private Seeker seeker;

//    // 공고 테이블과 n:1 -> announcementIdx
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "announcement_idx")
//    private Announcement announcement;
}
