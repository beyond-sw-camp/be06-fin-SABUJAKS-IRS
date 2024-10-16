package com.service.member.entity;

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
// 지원자 엔티티
public class Seeker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx; // idx

    @Column(nullable = false, length = 100, unique = true)
    private String email; // 이메일

    private String password; // 비밀번호

    private String name; // 지원자명

    private String nickname; // 닉네임

    private Boolean gender; // 성별 남성(0), 여성(1)

    private String birth; // 생년월일

    private String phoneNumber; // 전화 번호

    private String address; // 주소

    private String profileImage; // 프로필 이미지 Url

    private String socialType; // 소셜 계정 타입

    private Boolean isEmailAuth; // 이메일 인증 여부

    private Boolean isInactive; // 계정 비활성화 여부

    private String role; // 시스템 권한

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

//    // 회원정보 테이블과 1:n
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "seeker")
//    private List<ResumeInfo> resumeInfoList;
//
//    // 면접 일정 테이블과 n:1
//    @OneToMany(fetch = FetchType.EAGER, mappedBy = "seeker")
//    private List<InterviewParticipate> interviewParticipateList = new ArrayList<>();
//
//    // 공고지원서 테이블과 1:n
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "seeker")
//    private List<Resume> resumeList;
//
//    // 알람 테이블과 1:n
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "seeker")
//    private List<Alarm> alarmList;
//
//    // 면접 일정 조율 테이블과 1:n
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "seeker")
//    private List<ReSchedule> reScheduleList;
//
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "seeker")
//    private List<TotalProcess> totalProcessList;
}
