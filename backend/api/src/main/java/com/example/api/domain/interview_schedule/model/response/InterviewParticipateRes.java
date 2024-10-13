package com.example.api.domain.interview_schedule.model.response;

import com.example.common.domain.auth.model.entity.Estimator;
import com.example.common.domain.auth.model.entity.Seeker;
import lombok.Builder;
import lombok.Getter;

import java.util.List;


@Builder
@Getter
public class InterviewParticipateRes {
    private Long idx; // InterviewScheduleLists의 고유 ID
    private Long interviewScheduleIdx; // InterviewSchedule의 ID
    private String interviewDate; // InterviewSchedule의 날짜 정보
    private String interviewStart; // InterviewSchedule의 시작 시간
    private String interviewEnd; // InterviewSchedule의 종료 시간
    private String uuid; // 고유 면접방 uuid
    private List<Seeker> seekerList;
    private List<Estimator> estimatorList;
    private Long teamIdx; // Team의 ID
    private String teamName; // 팀 이름
}
