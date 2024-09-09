package com.sabujaks.irs.domain.interview_schedule.service;

import com.sabujaks.irs.domain.interview_schedule.model.entity.InterviewSchedule;
import com.sabujaks.irs.domain.interview_schedule.model.request.InterviewScheduleReq;
import com.sabujaks.irs.domain.interview_schedule.model.response.InterviewScheduleRes;
import com.sabujaks.irs.domain.interview_schedule.repository.InterviewScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InterviewScheduleService {

    private final InterviewScheduleRepository interviewScheduleRepository;

    public InterviewScheduleRes create(InterviewScheduleReq dto, String uuid) {
        InterviewSchedule interviewSchedule = interviewScheduleRepository.save(InterviewSchedule.builder()
                        .isOnline(Boolean.parseBoolean(dto.getIsOnline()))
                        .interviewDate(dto.getInterviewDate())
                        .interviewStart(dto.getInterviewStart())
                        .interviewEnd(dto.getInterviewEnd())
                        .uuid(uuid)
                        .build());


        return InterviewScheduleRes.builder()
                .idx(interviewSchedule.getIdx())
                .seekerList(dto.getSeekerList())
                .interviewerList(dto.getInterviewerList())
                .isOnline(interviewSchedule.getIsOnline())
                .interviewDate(interviewSchedule.getInterviewDate())
                .interviewStart(interviewSchedule.getInterviewStart())
                .interviewEnd(interviewSchedule.getInterviewEnd())
                .build();
    }
}
