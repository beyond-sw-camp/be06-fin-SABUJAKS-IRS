package com.sabujaks.irs.domain.announce.model.request;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnnounceRegisterReq {
    private Boolean selectForm; // 양식 선택
    private String title; // 공고제목
    private List<String> jobCategoryList = new ArrayList<>(); // 직무 카테고리
    private String jobTitle; // 모집분야명
    private Integer recruitedNum; // 모집인원
    private String careerBase; // 경력인지 신입인지 무관인지 알바인지 등등 뽑는 기준
    private String intro; // 회사소개
    private String positionQuali; // 포지션&자격요건
    private String jobType; // 근무형태
    private String salary; // 급여
    private String conditions; // 근무조건
    private String region; // 근무지역
    private String benefits; // 복지&혜택
    private String process; // 전형절차
    private String announcementStart; // 모집시작
    private String announcementEnd; // 모집마감
    private Integer interviewNum; // 면접횟수
    private String note; // 유의사항
    private String imgUrl; // 공고 img

}
