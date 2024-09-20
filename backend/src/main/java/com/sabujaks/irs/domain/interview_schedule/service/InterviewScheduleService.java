package com.sabujaks.irs.domain.interview_schedule.service;

import com.sabujaks.irs.domain.alarm.model.entity.Alarm;
import com.sabujaks.irs.domain.alarm.repository.AlarmRepository;
import com.sabujaks.irs.domain.announcement.model.entity.Announcement;
import com.sabujaks.irs.domain.announcement.repository.AnnouncementRepository;
import com.sabujaks.irs.domain.auth.model.entity.Seeker;
import com.sabujaks.irs.domain.interview_schedule.model.entity.*;
import com.sabujaks.irs.domain.auth.model.entity.Estimator;
import com.sabujaks.irs.domain.auth.model.entity.Recruiter;
import com.sabujaks.irs.domain.auth.repository.EstimatorRepository;
import com.sabujaks.irs.domain.auth.repository.RecruiterRepository;
import com.sabujaks.irs.domain.auth.repository.SeekerRepository;
import com.sabujaks.irs.domain.interview_schedule.model.request.InterviewScheduleReq;
import com.sabujaks.irs.domain.interview_schedule.model.request.ReScheduleReq;
import com.sabujaks.irs.domain.interview_schedule.model.response.InterviewScheduleRes;
import com.sabujaks.irs.domain.interview_schedule.model.response.ReScheduleRes;
import com.sabujaks.irs.domain.interview_schedule.repository.InterviewParticipateRepository;
import com.sabujaks.irs.domain.interview_schedule.repository.InterviewScheduleRepository;
import com.sabujaks.irs.domain.interview_schedule.repository.ReScheduleRepository;
import com.sabujaks.irs.domain.interview_schedule.repository.TeamRepository;
import com.sabujaks.irs.global.common.exception.BaseException;
import com.sabujaks.irs.global.common.responses.BaseResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class InterviewScheduleService {
    private final PasswordEncoder passwordEncoder;
    private final InterviewScheduleRepository interviewScheduleRepository;
    private final SeekerRepository seekerRepository;
    private final InterviewParticipateRepository interviewParticipateRepository;
    private final EstimatorRepository estimatorRepository;
    private final AnnouncementRepository announcementRepository;
    private final TeamRepository teamRepository;
    private final RecruiterRepository recruiterRepository;
    private final AlarmRepository alarmRepository;
    private final ReScheduleRepository reScheduleRepository;

//    public InterviewScheduleRes create(CustomUserDetails customUserDetails, InterviewScheduleReq dto) throws BaseException {
//        String uuid = UUID.randomUUID().toString();
//        Recruiter recruiter = recruiterRepository.findByRecruiterIdx(customUserDetails.getIdx())
//        .orElseThrow(() -> new BaseException(BaseResponseMessage.MEMBER_NOT_FOUND));
//        Announcement announcement = announceRepository.findByAnnounceIdx(dto.getAnnouncementIdx())
//        .orElseThrow(() -> new BaseException(BaseResponseMessage.ANNOUNCEMENT_SEARCH_FAIL));
//        Team team = teamRepository.findByIdx(dto.getTeamIdx())
//        .orElseThrow(() -> new BaseException(BaseResponseMessage.TEAM_NOT_FOUND));
//
//        InterviewSchedule interviewSchedule = InterviewSchedule.builder()
//                .announcement(announcement)
//                .recruiter(recruiter)
//                .isOnline(dto.getIsOnline())
//                .interviewDate(dto.getInterviewDate())
//                .interviewStart(dto.getInterviewStart())
//                .interviewEnd(dto.getInterviewEnd())
//                .uuid(uuid)
//                .careerBase(dto.getCareerBase())
//                .recruiter(recruiter)
//                .announcement(announcement)
//                .build();
//        interviewScheduleRepository.save(interviewSchedule);
//
//        List<Estimator> estimatorList = new ArrayList<>();
//        List<Seeker> seekerList = new ArrayList<>();
//        List<String> estimatorPasswordList = new ArrayList<>();
//        for(Long seekerIdx : dto.getSeekerList()) {
//            for(String estimatorEmail : dto.getEstimatorList()) {
//                String estimatorPassword = UUID.randomUUID().toString();
//                Optional<Estimator> resultEstimator = estimatorRepository.findByEstimatorEmail(estimatorEmail);
//                Estimator estimator = null;
//                if(resultEstimator.isEmpty()){
//                    estimator = Estimator.builder()
//                            .role("ROLE_ESTIMATOR")
//                            .email(estimatorEmail)
//                            // UUID로 저장하고, UUID를 이메일로 전송
//                            .password(passwordEncoder.encode("qwer1234"))
//                            .emailAuth(true)
//                            .build();
//                    estimatorRepository.save(estimator);
//                } else {
//                    estimator = resultEstimator.get();
//                }
//                estimatorList.add(estimator);
//                InterviewParticipate interviewParticipate = interviewParticipateRepository.save(InterviewParticipate.builder()
//                        .seeker(seekerRepository.findBySeekerIdx(seekerIdx).orElseThrow(() -> new BaseException(BaseResponseMessage.MEMBER_NOT_FOUND)))
//                        .estimator(estimator)
//                        .team(team)
//                        .interviewSchedule(interviewSchedule)
//                        .build());
//                interviewParticipateRepository.save(interviewParticipate);
//            }
//        }
//        return InterviewScheduleRes.builder()
//                .idx(interviewSchedule.getIdx())
//                .estimatorList(dto.getEstimatorList())
//                .seekerList(dto.getSeekerList())
//                .isOnline(interviewSchedule.getIsOnline())
//                .interviewDate(interviewSchedule.getInterviewDate())
//                .interviewEnd(interviewSchedule.getInterviewEnd())
//                .interviewStart(interviewSchedule.getInterviewStart())
//                .uuid(interviewSchedule.getUuid())
//                .build();
//    }

    public InterviewScheduleRes create(InterviewScheduleReq dto) throws BaseException {
        String uuid = UUID.randomUUID().toString();
        Recruiter recruiter = recruiterRepository.findByRecruiterIdx(1L)
                .orElseThrow(() -> new BaseException(BaseResponseMessage.MEMBER_NOT_FOUND));
        Announcement announcement = announcementRepository.findByAnnounceIdx(1L)
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
                .announcement(announcement)
                .build();
        interviewScheduleRepository.save(interviewSchedule);

        System.out.println("@@@@@@@면접관 등록 시작");

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
                            .name("면접관")
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

        System.out.println("@@@@@@@알람 등록 시작");
        // Alarm 저장 로직
        alarmRepository.save(Alarm.builder()
                        .type("면접일정")
                        .status(false)
                        .message("귀하의 면접일정은 다음과 같습니다.")
                        .seeker(seekerRepository.findBySeekerIdx(1L).orElseThrow(() -> new BaseException(BaseResponseMessage.MEMBER_NOT_FOUND)))
                        .interviewSchedule(interviewSchedule)
                        .createdAt(LocalDateTime.now())
                        .url("iehglskjfasel-welifhwlkgjwelifj")
                        .build());

        return InterviewScheduleRes.builder()
                .idx(interviewSchedule.getIdx())
                .estimatorList(dto.getEstimatorList())
                .seekerList(dto.getSeekerList())
                .isOnline(interviewSchedule.getIsOnline())
                .interviewDate(interviewSchedule.getInterviewDate())
                .interviewEnd(interviewSchedule.getInterviewEnd())
                .interviewStart(interviewSchedule.getInterviewStart())
                .uuid(interviewSchedule.getUuid())
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

    public List<InterviewScheduleRes> readAllExp() {
        List<InterviewSchedule> result = interviewScheduleRepository.findByCareerBase("경력");

        List<InterviewScheduleRes> interviewScheduleList = new ArrayList<>();
        for(InterviewSchedule interviewSchedule : result) {
            interviewScheduleList.add(InterviewScheduleRes.builder()
                    .idx(interviewSchedule.getIdx())
                    .isOnline(interviewSchedule.getIsOnline())
                    .interviewDate(interviewSchedule.getInterviewDate())
                    .interviewStart(interviewSchedule.getInterviewStart())
                    .interviewEnd(interviewSchedule.getInterviewEnd())
                    .build());
        }

        return interviewScheduleList;
    }

    public ReScheduleRes createReSchedule(ReScheduleReq dto) throws BaseException {

        InterviewSchedule interviewSchedule = interviewScheduleRepository.findByInterviewScheduleIdx(dto.getInterviewScheduleIdx())
                                        .orElseThrow(() -> new BaseException(BaseResponseMessage.INTERVIEW_SCHEDULE_NOT_FOUND));
        Seeker seeker = seekerRepository.findBySeekerIdx(1L).orElseThrow(() -> new BaseException(BaseResponseMessage.MEMBER_NOT_FOUND));

        ReSchedule reSchedule = reScheduleRepository.save(ReSchedule.builder()
                        .interviewStart(dto.getInterviewStart())
                        .interviewEnd(dto.getInterviewEnd())
                        .interviewSchedule(interviewSchedule)
                        .seeker(seeker)
                        .build());

        return ReScheduleRes.builder()
                .idx(reSchedule.getIdx())
                .interviewStart(reSchedule.getInterviewStart())
                .interviewEnd(reSchedule.getInterviewEnd())
                .interviewScheduleIdx(reSchedule.getInterviewSchedule().getIdx())
                .seekerIdx(reSchedule.getSeeker().getIdx())
                .build();
    }

    public List<ReScheduleRes> readAllReSchedule(Long announcementIdx) throws BaseException {
        List<InterviewSchedule> result = interviewScheduleRepository.findByAnnouncementIdx(announcementIdx).orElseThrow(() -> new BaseException(BaseResponseMessage.INTERVIEW_SCHEDULE_NOT_FOUND));
        List<ReScheduleRes> reScheduleResList = new ArrayList<>();

        for(InterviewSchedule interviewSchedule : result) {
            ReSchedule reSchedule = reScheduleRepository.findByInterviewScheduleIdx(interviewSchedule.getIdx());

            if(reSchedule != null) {
                reScheduleResList.add(ReScheduleRes.builder()
                        .idx(reSchedule.getIdx())
                        .interviewStart(reSchedule.getInterviewStart())
                        .interviewEnd(reSchedule.getInterviewEnd())
                        .interviewScheduleIdx(reSchedule.getInterviewSchedule().getIdx())
                        .seekerIdx(reSchedule.getSeeker().getIdx())
                        .build());
            }
        }

        return reScheduleResList;
    }
}