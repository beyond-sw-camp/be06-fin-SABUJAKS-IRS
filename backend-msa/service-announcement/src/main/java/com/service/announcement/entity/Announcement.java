package com.service.announcement.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Announcement { //공고
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private Long recruiterIdx; // msa_member

    @Column(length = 100)
    private String title; // 공고제목

    private Boolean selectForm; // 양식 선택

    private String imgUrl; // 공고 img

    private String jobCategory; // 직무 카테고리

    @Column(length = 100)
    private String jobTitle; // 모집분야명

    private Integer recruitedNum; // 모집인원

    @Column(length = 20)
    private String careerBase; // 경력 (경력인지 신입인지 무관인지 알바인지 등등 뽑는 기준)

    @Column(columnDefinition = "TEXT")
    private String positionQuali; // 포지션&자격요건 (주요업무, 근무부서, 직급직책, 필수/우대조건)

    @Column(length = 100)
    private String region; // 근무지역

    @Column(length = 20)
    private String jobType; // 근무형태

    @Column(length = 100)
    private String salary; // 급여

    @Column(columnDefinition = "TEXT")
    private String conditions; // 근무조건 (근무시간, 근무요일, 출퇴근시간)

    @Column(columnDefinition = "TEXT")
    private String benefits; // 복지&혜택

    private String managerName; // 인사담당자명

    private String managerContact; // 인사담당자 연락처

    private String managerEmail; // 인사담당자 이메일

    @Column(columnDefinition = "TEXT")
    private String intro; // 회사소개

    private String announcementStart; // 모집시작

    private String announcementEnd; // 모집마감

    private Integer interviewNum; // 면접횟수

    @Column(columnDefinition = "TEXT")
    private String process; // 전형절차

    @Column(columnDefinition = "TEXT")
    private String note; // 유의사항

    @Column(nullable = false, unique = true, updatable = false)
    private String uuid; // UUID 인증코드

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

      // msa_member -> recruiterIdx
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "recruiter_idx")
//    private Recruiter recruiter; // 채용담당자 외래키

    @OneToMany(mappedBy = "announcement" ,fetch = FetchType.LAZY)
    private List<CustomResumeForm> customResumeFormList; // 지원서 맞춤양식 테이블과 관계
    @OneToMany(mappedBy = "announcement" ,fetch = FetchType.LAZY)
    private List<CustomLetterForm> CustomLetterFormList; // 자기소개서 맞춤양식 테이블과 관계
//    @OneToMany(mappedBy = "announcement", fetch = FetchType.LAZY)
//    private List<InterviewEvaluateForm> interviewEvaluateFormList = new ArrayList<>();
//    @OneToMany(mappedBy = "announcement", fetch = FetchType.LAZY)
//    private List<TotalProcess> totalProcessList;
    // 공고 등록 시 uuid 생성
    @PrePersist
    public void createUUID() {
        this.uuid = UUID.randomUUID().toString(); // UUID 자동 생성
    }
}