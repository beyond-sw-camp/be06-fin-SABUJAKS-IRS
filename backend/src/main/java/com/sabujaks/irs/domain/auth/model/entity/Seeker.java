package com.sabujaks.irs.domain.auth.model.entity;

import com.sabujaks.irs.domain.alarm.model.entity.Alarm;
import com.sabujaks.irs.domain.interview_evaluate.model.entity.InterviewEvaluate;
import com.sabujaks.irs.domain.interview_evaluate.model.entity.InterviewEvaluateForm;
import com.sabujaks.irs.domain.interview_schedule.model.entity.InterviewParticipate;
import com.sabujaks.irs.domain.interview_schedule.model.entity.InterviewSchedule;
import com.sabujaks.irs.domain.interview_schedule.model.entity.ReSchedule;
import com.sabujaks.irs.domain.resume.model.entity.Resume;
import com.sabujaks.irs.domain.resume.model.entity.ResumeInfo;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// 지원자 엔티티
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Seeker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    @Column(nullable = false, length = 100, unique = true)
    private String email; // 이메일

    @Setter
    private String password; // 비밀번호
    private String name; // 지원자명
    private String nickname; // 닉네임
    private Boolean gender; // 성별 0 남성, 1 여성
    private String birth; // 생년월일
    private String phoneNumber; // 전화 번호
    private String address; // 주소
    private Boolean emailAuth; // 이메일 인증
    private Boolean inactive; // 계정 비활성화 상태
    private String role; // 접근 권한
    private String socialType; // 소셜 계정 여부
    private String profileImage; // 프로필 이미지

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    // 회원정보 테이블과 1:n
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "seeker")
    private List<ResumeInfo> resumeInfoList;

    // 면접 일정 테이블과 n:1
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "seeker")
    private List<InterviewParticipate> interviewParticipateList = new ArrayList<>();

    // 공고지원서 테이블과 1:n
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "seeker")
    private List<Resume> resumeList;

    // 알람 테이블과 1:n
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "seeker")
    private List<Alarm> alarmList;

    // 면접 일정 조율 테이블과 1:n
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "seeker")
    private List<ReSchedule> reScheduleList;

    @OneToMany(mappedBy = "seeker", fetch = FetchType.LAZY)
    private List<InterviewEvaluate> interviewEvaluateList = new ArrayList<>();

}
