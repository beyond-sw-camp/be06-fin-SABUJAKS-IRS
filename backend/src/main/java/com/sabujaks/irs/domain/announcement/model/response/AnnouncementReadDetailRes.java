package com.sabujaks.irs.domain.announcement.model.response;

import com.sabujaks.irs.domain.company.model.response.BenefitCategory;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnnouncementReadDetailRes {
    private Long announcementIdx;
    private Long companyIdx;

    /*******공고 쪽 정보*******/
    // 공고 기본 설정
    private String title; // 공고제목
    private Boolean selectForm; // 양식 선택
    private String imgUrl; // 공고 img

    // 모집분야
    private String jobCategory; // 직무 카테고리
    private String jobTitle; // 모집분야명
    private Integer recruitedNum; // 모집인원
    private String careerBase; // 경력 (경력인지 신입인지 무관인지 알바인지 등등 뽑는 기준)
    private String positionQuali; // 포지션&자격요건 (주요업무, 근무부서, 직급직책, 필수/우대조건)

    // 지원자격/근무조건
    private String region; // 근무지역
    private String jobType; // 근무형태
    private String salary; // 급여
    private String conditions; // 근무조건 (근무시간, 근무요일, 출퇴근시간)

    // 기업 복리후생
    private String benefits; // 복지&혜택

    // 인사담당자/기업정보
    private String managerName; // 인사담당자명
    private String managerContact; // 인사담당자 연락처
    private String managerEmail; // 인사담당자 이메일
    private String intro; // 회사소개

    // 채용절차
    private String announcementStart; // 모집시작
    private String announcementEnd; // 모집마감
    private Integer interviewNum; // 면접횟수
    private String process; // 전형절차

    // 유의사항
    private String note; // 유의사항


    /*******기업 쪽 정보*******/
    private String companyIndustry; // 산업(업종) (솔루션 등)
    private String companyName; // 기업명
    private String companyType; // 기업구분(중견기업, 벤처기업 등)
    private String companyInfo; // 기업소개
    private String companyCapital; // 자본금
    private String companyTotalEmp; // 사원수
    private String companyEstablishDate; // 설립일
    private String companySales; // 매출액
    private String companyBusiness; // 주요사업
    private String companyUrl; // 홈페이지url
    private String companyAddress; // 기업주소
    private List<BenefitCategory> companyBenefitsDataList; // 복리후생 카테고리 리스트

}
