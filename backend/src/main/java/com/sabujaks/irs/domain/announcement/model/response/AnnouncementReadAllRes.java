package com.sabujaks.irs.domain.announcement.model.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnnouncementReadAllRes {
    private Long announcementIdx;
    private String companyName; // 기업명
    private String announcementTitle; // 공고명
    private String jobTitle; // 모집분야명
    private String careerBase; // 신입경력
    private String region; // 근무지역
    private String announcementEnd; // 마감일
    private String companyInfo; // 기업소개
    private List<String> imgList; // 기업이미지 url 리스트
    // 조회수
}
