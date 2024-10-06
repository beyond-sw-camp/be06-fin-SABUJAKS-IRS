package com.sabujaks.irs.domain.company.model.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyReadRes {
    private String saved; // 등록됐는지 안됐는지 확인용
    private Long companyIdx;

    // 기업인증테이블에서 가져오는 값들
    private String ceoName; // 대표자 명
    private String crn; // 사업자 등록 번호
    private String openedAt; // 개업일자

    private String industry; // 산업
    private String name; // 기업 명
    private String type; // 기업구분
    private String companyInfo; // 기업소개
    private String capital; // 자본금
    private String totalEmp; // 사원수
    private String establishDate; // 설립일
    private String sales; // 매출액
    private String business; // 주요사업
    private String homeUrl; // 홈페이지url
    private String address; // 기업주소
    private List<String> imgUrlList; // 회사 이미지 리스트
    private List<BenefitCategory> companyBenefitsList; // 회사 복리후생 리스트

}
