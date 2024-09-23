package com.sabujaks.irs.domain.interview_schedule.service;

import com.sabujaks.irs.domain.alarm.model.entity.Alarm;
import com.sabujaks.irs.domain.alarm.repository.AlarmRepository;
import com.sabujaks.irs.domain.announcement.model.entity.Announcement;
import com.sabujaks.irs.domain.announcement.repository.AnnouncementRepository;
import com.sabujaks.irs.domain.auth.model.entity.Seeker;
import com.sabujaks.irs.domain.auth.model.response.EstimatorInfoGetRes;
import com.sabujaks.irs.domain.auth.model.response.SeekerInfoGetRes;
import com.sabujaks.irs.domain.interview_schedule.model.entity.*;
import com.sabujaks.irs.domain.auth.model.entity.Estimator;
import com.sabujaks.irs.domain.auth.model.entity.Recruiter;
import com.sabujaks.irs.domain.auth.repository.EstimatorRepository;
import com.sabujaks.irs.domain.auth.repository.RecruiterRepository;
import com.sabujaks.irs.domain.auth.repository.SeekerRepository;
import com.sabujaks.irs.domain.interview_schedule.model.request.InterviewScheduleReq;
import com.sabujaks.irs.domain.interview_schedule.model.request.InterviewScheduleUpdateReq;
import com.sabujaks.irs.domain.interview_schedule.model.request.ReScheduleReq;
import com.sabujaks.irs.domain.interview_schedule.model.response.InterviewParticipateReadRes;
import com.sabujaks.irs.domain.interview_schedule.model.response.InterviewScheduleRes;
import com.sabujaks.irs.domain.interview_schedule.model.response.ReScheduleRes;
import com.sabujaks.irs.domain.interview_schedule.repository.InterviewParticipateRepository;
import com.sabujaks.irs.domain.interview_schedule.repository.InterviewScheduleRepository;
import com.sabujaks.irs.domain.interview_schedule.repository.ReScheduleRepository;
import com.sabujaks.irs.domain.interview_schedule.repository.TeamRepository;
import com.sabujaks.irs.global.common.exception.BaseException;
import com.sabujaks.irs.global.common.responses.BaseResponseMessage;
import com.sabujaks.irs.global.security.CustomUserDetails;
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

    public InterviewScheduleRes create(CustomUserDetails customUserDetails, InterviewScheduleReq dto) throws BaseException {
        String uuid = uuidCheck(dto);
        Recruiter recruiter = recruiterRepository.findByRecruiterIdx(customUserDetails.getIdx())
                .orElseThrow(() -> new BaseException(BaseResponseMessage.MEMBER_NOT_FOUND));
        Announcement announcement = announcementRepository.findByAnnounceIdx(dto.getAnnouncementIdx())
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
                .team(team)
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

        System.out.println("@@@@@@@알람 등록 시작");
        // Alarm 저장 로직
        for(Long seekerIdx : dto.getSeekerList()) {
            alarmRepository.save(Alarm.builder()
                    .type("면접일정")
                    .status(false)
                    .message("귀하의 면접일정은 다음과 같습니다.")
                    .seeker(seekerRepository.findBySeekerIdx(seekerIdx).orElseThrow(() -> new BaseException(BaseResponseMessage.MEMBER_NOT_FOUND)))
                    .interviewSchedule(interviewSchedule)
                    .createdAt(LocalDateTime.now())
                    .url("iehglskjfasel-welifhwlkgjwelifj")
                    .build());
        }
        return InterviewScheduleRes.builder()
                .idx(interviewSchedule.getIdx())
                .isOnline(interviewSchedule.getIsOnline())
                .interviewDate(interviewSchedule.getInterviewDate())
                .interviewEnd(interviewSchedule.getInterviewEnd())
                .interviewStart(interviewSchedule.getInterviewStart())
                .uuid(interviewSchedule.getUuid())
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

    public List<InterviewScheduleRes> readAll(String careerBase, Long idx) throws BaseException {
        Optional<List<InterviewSchedule>> result;
        if(careerBase.equals("전체")) {
            result = interviewScheduleRepository.findByAnnouncementIdx(idx);
        } else {
            result = interviewScheduleRepository.findByCareerBaseAndAnnouncementIdx(careerBase, idx);
        }

        List<InterviewScheduleRes> interviewScheduleList = new ArrayList<>();

        if(!result.isEmpty()) {
            for (InterviewSchedule interviewSchedule : result.get()) {
                List<SeekerInfoGetRes> seekerInfoGetResList = new ArrayList<>();
                List<InterviewParticipate> participateResult = interviewParticipateRepository.findByInterviewScheduleIdx(interviewSchedule.getIdx()).orElseThrow(() ->
                        new BaseException(BaseResponseMessage.INTERVIEW_PARTICIPATE_NOT_FOUND));

                for(InterviewParticipate interviewParticipate : participateResult) {
                    seekerInfoGetResList.add(SeekerInfoGetRes.builder()
                            .idx(interviewParticipate.getIdx())
                            .name(seekerRepository.findBySeekerIdx(interviewParticipate.getSeeker().getIdx()).get().getName())
                            .email(seekerRepository.findBySeekerIdx(interviewParticipate.getSeeker().getIdx()).get().getEmail())
                            .build());
                }
                interviewScheduleList.add(InterviewScheduleRes.builder()
                        .idx(interviewSchedule.getIdx())
                        .isOnline(interviewSchedule.getIsOnline())
                        .interviewDate(interviewSchedule.getInterviewDate())
                        .interviewStart(interviewSchedule.getInterviewStart())
                        .interviewEnd(interviewSchedule.getInterviewEnd())
                        .teamIdx(interviewSchedule.getTeam().getIdx())
                        .seekerList(seekerInfoGetResList)
                        .build());
            }
        }

        return interviewScheduleList;
    }

    public InterviewScheduleRes read(Long interviewScheduleIdx) throws BaseException {
        InterviewSchedule result = interviewScheduleRepository.findByInterviewScheduleIdx(interviewScheduleIdx).orElseThrow(() ->
                new BaseException(BaseResponseMessage.INTERVIEW_SCHEDULE_NOT_FOUND));

        List<InterviewParticipate> participateResult = interviewParticipateRepository.findByInterviewScheduleIdx(interviewScheduleIdx).orElseThrow(() ->
                new BaseException(BaseResponseMessage.INTERVIEW_PARTICIPATE_NOT_FOUND));

        List<EstimatorInfoGetRes> estimatorInfoGetResList = new ArrayList<>();
        List<SeekerInfoGetRes> seekerInfoGetResList = new ArrayList<>();

        // estimator 정보 담기
        for(InterviewParticipate interviewParticipate : participateResult) {
            estimatorInfoGetResList.add(EstimatorInfoGetRes.builder()
                    .idx(interviewParticipate.getEstimator().getIdx())
                    .email(interviewParticipate.getEstimator().getEmail())
                    .name(interviewParticipate.getEstimator().getName())
                    .build());
        }

        // seeker 정보 담기
        for(InterviewParticipate interviewParticipate : participateResult) {
            seekerInfoGetResList.add(SeekerInfoGetRes.builder()
                    .idx(interviewParticipate.getSeeker().getIdx())
                    .email(interviewParticipate.getSeeker().getEmail())
                    .name(interviewParticipate.getSeeker().getName())
                    .build());
        }

        InterviewScheduleRes interviewScheduleRes = InterviewScheduleRes.builder()
                .idx(result.getIdx())
                .isOnline(result.getIsOnline())
                .interviewDate(result.getInterviewDate())
                .interviewEnd(result.getInterviewEnd())
                .interviewStart(result.getInterviewStart())
                .teamIdx(result.getTeam().getIdx())
                .uuid(result.getUuid())
                .estimatorList(estimatorInfoGetResList)
                .seekerList(seekerInfoGetResList)
                .build();
        return interviewScheduleRes;
    }

    public void update(InterviewScheduleUpdateReq dto) throws BaseException {
        for (Long seekerIdx : dto.getSeekerList()) {
            Long interviewScheduleIdx = dto.getInterviewScheduleIdx();

            InterviewParticipate interviewParticipate = interviewParticipateRepository
                    .findByInterviewScheduleIdxAndSeekerIdx(interviewScheduleIdx, seekerIdx)
                    .orElseThrow(() -> new BaseException(BaseResponseMessage.INTERVIEW_PARTICIPATE_NOT_FOUND));

            if (interviewParticipate != null) {
                interviewParticipate.setStatus(false);
                interviewParticipateRepository.save(interviewParticipate);
            }
        }
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
                .interviewScheduleRes(InterviewScheduleRes.builder()
                        .idx(reSchedule.getInterviewSchedule().getIdx())
                        .isOnline(reSchedule.getInterviewSchedule().getIsOnline())
                        .interviewDate(reSchedule.getInterviewSchedule().getInterviewDate())
                        .interviewStart(reSchedule.getInterviewSchedule().getInterviewStart())
                        .interviewEnd(reSchedule.getInterviewSchedule().getInterviewEnd())
                        .teamIdx(reSchedule.getInterviewSchedule().getTeam().getIdx())
                        .uuid(reSchedule.getInterviewSchedule().getUuid())
                        .build())
                .seekerInfoGetRes(SeekerInfoGetRes.builder()
                        .idx(reSchedule.getSeeker().getIdx())
                        .email(reSchedule.getSeeker().getEmail())
                        .name(reSchedule.getSeeker().getName())
                        .build())
                .build();
    }

    public List<ReScheduleRes> readAllReSchedule(Long announcementIdx) throws BaseException {
        List<InterviewSchedule> result = interviewScheduleRepository.findByAnnouncementIdx(announcementIdx).orElseThrow(() -> new BaseException(BaseResponseMessage.INTERVIEW_SCHEDULE_NOT_FOUND));
        List<ReScheduleRes> reScheduleResList = new ArrayList<>();

        for(InterviewSchedule interviewSchedule : result) {
            ReSchedule reSchedule = reScheduleRepository.findByInterviewScheduleIdx(interviewSchedule.getIdx());
            if(reSchedule == null) {
                continue;
            }
            System.out.println("?????@?@?@?@?");
            System.out.println(reSchedule.getIdx());
            System.out.println(reSchedule.getInterviewStart());
            System.out.println(reSchedule.getInterviewEnd());
            System.out.println(reSchedule.getSeeker().getIdx());
            System.out.println("?????@?@?@?@?");
            Seeker seeker = seekerRepository.findBySeekerIdx(reSchedule.getSeeker().getIdx()).get();

            if(reSchedule != null) {
                reScheduleResList.add(ReScheduleRes.builder()
                        .idx(reSchedule.getIdx())
                        .interviewStart(reSchedule.getInterviewStart())
                        .interviewEnd(reSchedule.getInterviewEnd())
                        .interviewScheduleRes(InterviewScheduleRes.builder()
                                .idx(interviewSchedule.getIdx())
                                .isOnline(interviewSchedule.getIsOnline())
                                .interviewDate(interviewSchedule.getInterviewDate())
                                .interviewStart(interviewSchedule.getInterviewStart())
                                .interviewEnd(interviewSchedule.getInterviewEnd())
                                .teamIdx(interviewSchedule.getTeam().getIdx())
                                .uuid(interviewSchedule.getUuid())
                                .build())
                        .seekerInfoGetRes(SeekerInfoGetRes.builder()
                                .idx(seeker.getIdx())
                                .email(seeker.getEmail())
                                .name(seeker.getName())
                                .build())
                        .build());
            }
        }

        return reScheduleResList;
    }
}