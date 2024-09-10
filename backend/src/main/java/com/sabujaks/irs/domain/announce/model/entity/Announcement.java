package com.sabujaks.irs.domain.announce.model.entity;

import com.sabujaks.irs.domain.auth.model.entity.Recruiter;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Announcement { //공고
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(nullable = false, length = 50)
    private String title; // 공고제목

    @Column(nullable = false)
    private LocalDateTime announcement_start; // 모집시작
    @Column(nullable = false)
    private LocalDateTime announcement_end; // 모집마감

    @Column(nullable = false, length = 20)
    private String job_category; // 직무 카테고리
    @Column(columnDefinition = "TEXT")
    private String intro; // 회사소개
    @Column(nullable = false, columnDefinition = "TEXT")
    private String position_qual; // 포지션&자격요건

    @Column(nullable = false, columnDefinition = "TEXT")
    private String conditions; // 근무조건
    @Column(nullable = false, length = 20)
    private String region; // 근무지역

    @Column(nullable = false, columnDefinition = "TEXT")
    private String benefits; // 복지&혜택
    @Column(nullable = false, columnDefinition = "TEXT")
    private String process; // 전형절차

    @Column(columnDefinition = "TEXT")
    private String note; // 유의사항

    @Column(length = 100)
    private String img_url; // 공고 img

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruiter_idx")
    private Recruiter recruiter; // 채용담당자 외래키

    // 기업 외래키 추가 필요

    @OneToMany(mappedBy = "announcement" ,fetch = FetchType.LAZY)
    private List<CustomForm> CustomFormList = new ArrayList<>(); // 지원서 맞춤양식 테이블과 관계
    @OneToMany(mappedBy = "announcement" ,fetch = FetchType.LAZY)
    private List<CustomLetterForm> CustomLetterFormList = new ArrayList<>(); // 자기소개서 맞춤양식 테이블과 관계

    @Column(nullable = false, unique = true, updatable = false)
    private String uuid; // UUID 필드 추가

    // 공고 등록 시 uuid 생성, 공고등록step1이 없어서 테스트 못해봄! 11일에 할 예정, 아마 될거임
    @PrePersist
    public void createUUID() {
        this.uuid = UUID.randomUUID().toString(); // UUID 자동 생성
    }
}
