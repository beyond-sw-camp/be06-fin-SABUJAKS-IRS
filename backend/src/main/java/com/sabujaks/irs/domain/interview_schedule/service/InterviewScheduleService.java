package com.sabujaks.irs.domain.interview_schedule.service;

import com.sabujaks.irs.domain.announce.model.entity.Announcement;
import com.sabujaks.irs.domain.announce.repository.AnnounceRepository;
import com.sabujaks.irs.domain.auth.model.entity.Seeker;
import com.sabujaks.irs.domain.interview_schedule.model.entity.Estimator;
import com.sabujaks.irs.domain.auth.model.entity.Recruiter;
import com.sabujaks.irs.domain.auth.repository.EstimatorRepository;
import com.sabujaks.irs.domain.auth.repository.RecruiterRepository;
import com.sabujaks.irs.domain.auth.repository.SeekerRepository;
import com.sabujaks.irs.domain.interview_schedule.model.entity.InterviewParticipate;
import com.sabujaks.irs.domain.interview_schedule.model.entity.InterviewSchedule;
import com.sabujaks.irs.domain.interview_schedule.model.entity.Team;
import com.sabujaks.irs.domain.interview_schedule.model.request.InterviewScheduleReq;
import com.sabujaks.irs.domain.interview_schedule.model.response.InterviewScheduleRes;
import com.sabujaks.irs.domain.interview_schedule.repository.InterviewParticipateRepository;
import com.sabujaks.irs.domain.interview_schedule.repository.InterviewScheduleRepository;
import com.sabujaks.irs.domain.interview_schedule.repository.TeamRepository;
import com.sabujaks.irs.global.common.exception.BaseException;
import com.sabujaks.irs.global.common.responses.BaseResponseMessage;
import com.sabujaks.irs.global.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class InterviewScheduleService {
    private final PasswordEncoder passwordEncoder;
    private final InterviewScheduleRepository interviewScheduleRepository;
    private final SeekerRepository seekerRepository;
    private final InterviewParticipateRepository interviewParticipateRepository;
    private final EstimatorRepository estimatorRepository;
    private final AnnounceRepository announceRepository;
    private final TeamRepository teamRepository;
    private final RecruiterRepository recruiterRepository;

    public InterviewScheduleRes create(CustomUserDetails customUserDetails, InterviewScheduleReq dto) throws BaseException {
        String uuid = UUID.randomUUID().toString();
        Recruiter recruiter = recruiterRepository.findByRecruiterIdx(customUserDetails.getIdx())
        .orElseThrow(() -> new BaseException(BaseResponseMessage.MEMBER_NOT_FOUND));
        Announcement announcement = announceRepository.findByAnnounceIdx(dto.getAnnouncementIdx())
        .orElseThrow(() -> new BaseException(BaseResponseMessage.ANNOUNCEMENT_SEARCH_FAIL));
        Team team = teamRepository.findByIdx(dto.getTeamIdx())
        .orElseThrow(() -> new BaseException(BaseResponseMessage.TEAM_NOT_FOUND));

        InterviewSchedule interviewSchedule = InterviewSchedule.builder()
                .announcement(announcement)
                .recruiter(recruiter)
                .isOnline(dto.getIsOnline())
                .interviewDate(dto.getInterviewDate())
                .interviewStart(dto.getInterviewStart())
                .interviewEnd(dto.getInterviewEnd())
                .uuid(uuid)
                .careerBase(dto.getCareerBase())
                .recruiter(recruiter)
                .announcement(announcement)
                .build();
        interviewScheduleRepository.save(interviewSchedule);

        List<Estimator> estimatorList = new ArrayList<>();
        List<Seeker> seekerList = new ArrayList<>();
        List<String> estimatorPasswordList = new ArrayList<>();
        for(Long seekerIdx : dto.getSeekerList()) {
            for(String estimatorEmail : dto.getEstimatorList()) {
                String estimatorPassword = UUID.randomUUID().toString();
                Optional<Estimator> resultEstimator = estimatorRepository.findByEstimatorEmail(estimatorEmail);
                Estimator estimator = null;
                if(resultEstimator.isEmpty()){
                    estimator = Estimator.builder()
                            .role("ROLE_ESTIMATOR")
                            .email(estimatorEmail)
                            // UUID로 저장하고, UUID를 이메일로 전송
                            .password(passwordEncoder.encode("qwer1234"))
                            .emailAuth(true)
                            .build();
                    estimatorRepository.save(estimator);
                } else {
                    estimator = resultEstimator.get();
                }
                estimatorList.add(estimator);
                InterviewParticipate interviewParticipate = interviewParticipateRepository.save(InterviewParticipate.builder()
                        .seeker(seekerRepository.findBySeekerIdx(seekerIdx).orElseThrow(() -> new BaseException(BaseResponseMessage.MEMBER_NOT_FOUND)))
                        .estimator(estimator)
                        .team(team)
                        .interviewSchedule(interviewSchedule)
                        .build());
                interviewParticipateRepository.save(interviewParticipate);
            }
        }
        return InterviewScheduleRes.builder()
                .idx(interviewSchedule.getIdx())
                .estimatorList(dto.getEstimatorList())
                .seekerList(dto.getSeekerList())
                .isOnline(interviewSchedule.getIsOnline())
                .interviewDate(interviewSchedule.getInterviewDate())
                .interviewEnd(interviewSchedule.getInterviewEnd())
                .interviewStart(interviewSchedule.getInterviewStart())
                .build();
    }
//    public String uuidCheck(InterviewScheduleReq dto) {
//        String uuid = "";
//        List<InterviewSchedule> result = interviewScheduleRepository.findByInterviewDate(dto.getInterviewDate());
//
//        // 날짜가 없으면 생성
//        if(result.isEmpty()) {
//            uuid = UUID.randomUUID().toString();
//
//            return uuid;
//        }
//
//        for(InterviewSchedule interviewSchedule : result) {
//            if((interviewSchedule.getTeam().getIdx()).equals(dto.getTeamIdx())) {
//                uuid = interviewSchedule.getUuid();
//            }
//        }
//
//        if(uuid.isEmpty()) {
//            uuid = UUID.randomUUID().toString();
//        }
//
//        return uuid;
//    }
}