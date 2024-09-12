package com.sabujaks.irs.domain.interview_schedule.service;

import com.sabujaks.irs.domain.auth.model.entity.Seeker;
import com.sabujaks.irs.domain.auth.repository.SeekerRepository;
import com.sabujaks.irs.domain.interview_schedule.model.entity.InterviewSchedule;
import com.sabujaks.irs.domain.interview_schedule.model.entity.InterviewScheduleLists;
import com.sabujaks.irs.domain.interview_schedule.model.entity.Team;
import com.sabujaks.irs.domain.interview_schedule.model.request.InterviewScheduleReq;
import com.sabujaks.irs.domain.interview_schedule.model.response.InterviewScheduleListsRes;
import com.sabujaks.irs.domain.interview_schedule.model.response.InterviewScheduleRes;
import com.sabujaks.irs.domain.interview_schedule.repository.InterviewScheduleListsRepository;
import com.sabujaks.irs.domain.interview_schedule.repository.InterviewScheduleRepository;
import com.sabujaks.irs.domain.interview_schedule.repository.TeamRepository;
import com.sabujaks.irs.global.common.exception.BaseException;
import com.sabujaks.irs.global.common.responses.BaseResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InterviewScheduleService {

    private final InterviewScheduleRepository interviewScheduleRepository;
    private final SeekerRepository seekerRepository;
    private final InterviewScheduleListsRepository interviewScheduleListsRepository;
    private final TeamRepository teamRepository;

    public InterviewScheduleListsRes create(InterviewScheduleReq dto) throws BaseException {

        String uuid = uuidCheck(dto);

        InterviewSchedule interviewSchedule = interviewScheduleRepository.save(InterviewSchedule.builder()
                .isOnline(Boolean.parseBoolean(dto.getIsOnline()))
                .interviewDate(dto.getInterviewDate())
                .interviewStart(dto.getInterviewStart())
                .interviewEnd(dto.getInterviewEnd())
                .uuid(uuid)
                .careerBase(dto.getCareerBase())
                .team(teamRepository.findByIdx(dto.getTeamIdx())
                        .orElseThrow(() -> new BaseException(BaseResponseMessage.TEAM_NOT_FOUND)))
                .build());

        ArrayList<Seeker> seekerList = new ArrayList<>();
        for(Long seekerIdx : dto.getSeekerList()) {
            InterviewScheduleLists interviewScheduleLists = interviewScheduleListsRepository.save(InterviewScheduleLists.builder()
                    .seeker(seekerRepository.findBySeekerIdx(seekerIdx)
                            .orElseThrow(() -> new BaseException(BaseResponseMessage.MEMBER_NOT_FOUND)))
                    .interviewSchedule(interviewSchedule)
                    .build());

            seekerList.add(interviewScheduleLists.getSeeker());
        }



        return InterviewScheduleListsRes.builder()
                .interviewScheduleIdx(interviewSchedule.getIdx())
                .interviewDate(interviewSchedule.getInterviewDate())
                .interviewStart(interviewSchedule.getInterviewStart())
                .interviewEnd(interviewSchedule.getInterviewEnd())
                .uuid(interviewSchedule.getUuid())
                .seekerList(seekerList)
                .teamIdx(interviewSchedule.getTeam().getIdx())
                .teamName(interviewSchedule.getTeam().getTeamName())
                .build();
    }

    public String uuidCheck(InterviewScheduleReq dto) {
        String uuid = "";
        List<InterviewSchedule> result = interviewScheduleRepository.findByInterviewDate(dto.getInterviewDate());

        // 날짜가 없으면 생성
        if(result.isEmpty()) {
            uuid = UUID.randomUUID().toString();

            return uuid;
        }

        for(InterviewSchedule interviewSchedule : result) {
            if((interviewSchedule.getTeam().getIdx()).equals(dto.getTeamIdx())) {
                uuid = interviewSchedule.getUuid();
            }
        }

        if(uuid.isEmpty()) {
            uuid = UUID.randomUUID().toString();
        }

        return uuid;
    }

//    public InterviewScheduleRes readAllExp() {
//        InterviewSchedule interviewSchedule = interviewScheduleRepository.findByCareerBase();
//    }
}
