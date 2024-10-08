package com.sabujaks.irs.domain.resume.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PersonalInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false, length = 10)
    private String birth;

    @Column(nullable = false, length = 20)
    private String gender; // 0 남성, 1 여성

    @Column(nullable = false, length = 30)
    private String email;

    @Column(nullable = false, length = 30)
    private String phone;

    @Column(length = 30)
    private String tel;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String profileImg;

    // 지원정보 테이블과 1:1
    @OneToOne(fetch = FetchType.LAZY)
    private ResumeInfo resumeInfo;
}
