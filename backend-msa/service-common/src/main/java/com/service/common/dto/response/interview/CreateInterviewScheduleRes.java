package com.service.common.dto.response.interview;

import com.service.common.dto.feign.ReadEstimatorRes;
import com.service.common.dto.feign.ReadSeekerRes;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class CreateInterviewScheduleRes {
    private Long idx;
    private List<ReadSeekerRes> readSeekerResList;
    private List<ReadEstimatorRes> readEstimatorResList;
    private Boolean isOnline;
    private String interviewDate;
    private String interviewStart;
    private String interviewEnd;
    private Integer interviewNum;
    private String uuid;
    private String careerBase;
    private Long teamIdx;
    private String companyName;
    private String announcementTitle;
    private Integer countInterviewSchedule;
}
