package com.sabujaks.irs.domain.announcement.model.response;

import com.sabujaks.irs.domain.auth.model.response.RecruiterRes;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class AnnouncementReadAllRes2 {
    private Long idx;

    // 공고 기본 설정
    private String title;
    private Boolean selectForm;
    private String imgUrl;

    // 모집분야
    private String jobCategory;
    private String jobTitle;
    private Integer recruitedNum;
    private String careerBase;
    private String positionQuali;

    // 지원자격/근무조건
    private String region;
    private String jobType;
    private String salary;
    private String conditions;

    // 기업 복리후생
    private String benefits;

    // 인사담당자/기업정보
    private String managerName;
    private String managerContact;
    private String managerEmail;
    private String intro;

    // 채용절차
    private String announcementStart;
    private String announcementEnd;
    private Integer interviewNum;
    private String process;

    // 유의사항
    private String note;

    // UUID 인증코드
    private String uuid;

    private Integer countInterviewSchedule;
    private Integer countReSchedule;

    // 관계 데이터
    private RecruiterRes recruiterRes;
}
