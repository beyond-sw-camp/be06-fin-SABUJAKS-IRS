package com.sabujaks.irs.domain.resume.model.entity;

import com.sabujaks.irs.domain.alarm.model.entity.Alarm;
import com.sabujaks.irs.domain.announcement.model.entity.Announcement;
import com.sabujaks.irs.domain.auth.model.entity.Seeker;
import com.sabujaks.irs.global.common.exception.BaseException;
import com.sabujaks.irs.global.common.responses.BaseResponseMessage;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
// 공고지원서 (각 공고에 지원한 지원서 정보)
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(nullable = false, length = 100)
    private String resumeTitle;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime resumedAt;

    // 지원정보 테이블과 n:1
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_info_idx")
    private ResumeInfo resumeInfo;

    // 공고 테이블과 n:1
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "announcement_idx")
    private Announcement announcement;

    // 회원(지원자) 테이블과 n:1
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seeker_idx")
    private Seeker seeker;

    // 알람 테이블과 1:n
    @OneToMany(mappedBy = "resume")
    private List<Alarm> alarm;
}
