package com.sabujaks.irs.domain.company.model.entity;

import com.sabujaks.irs.domain.auth.model.entity.Recruiter;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    // 아래 4가지는 기업 인증 테이블에서 조회하는 값들
    @Column(length = 30)
    private String ceoName; // 대표자 명
    private String crn; // 사업자 등록 번호
    private String openedAt; // 개업일자

    @Column(length = 20)
    private String industry; // 산업

    @Column(length = 30)
    private String name; // 기업 명

    @Column(length = 20)
    private String type; // 기업구분

    @Column(columnDefinition = "TEXT")
    private String companyInfo; // 기업소개

    private String capital; // 자본금

    @Column(length = 100)
    private String totalEmp; // 사원수

    private String establishDate; // 설립일

    private String sales; // 매출액

    private String business; // 주요사업

    private String url; // 홈페이지url

    @Column(columnDefinition = "TEXT")
    private String address; // 기업주소

    @OneToOne(fetch = FetchType.LAZY)
    private Recruiter recruiter; // 채용담당자 테이블과 1:1

    @OneToMany(mappedBy = "company" ,fetch = FetchType.LAZY)
    private List<CompanyImg> companyImgList = new ArrayList<>(); // 기업이미지 테이블과 관계

    @OneToMany(mappedBy = "company" ,fetch = FetchType.LAZY)
    private List<CompanyBenefits> companyBenefitsList = new ArrayList<>(); // 복리후생 테이블과 관계
}