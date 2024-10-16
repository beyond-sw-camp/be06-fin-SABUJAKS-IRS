package com.service.common.dto.response.interview;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ReadInterviewParticipateRes {
    private Long idx; // InterviewScheduleLists의 고유 ID
    private Long interviewScheduleIdx; // InterviewSchedule의 ID
    private String interviewDate; // InterviewSchedule의 날짜 정보
    private String interviewStart; // InterviewSchedule의 시작 시간
    private String interviewEnd; // InterviewSchedule의 종료 시간
    private String uuid; // 고유 면접방 uuid
    // TODO:
//    private List<Seeker> seekerList;
//    private List<Estimator> estimatorList;
    private Long teamIdx; // Team의 ID
    private String teamName; // 팀 이름
}
