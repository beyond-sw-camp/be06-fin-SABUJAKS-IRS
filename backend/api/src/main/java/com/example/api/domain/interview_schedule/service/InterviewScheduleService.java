package com.example.api.domain.interview_schedule.service;

import com.example.api.domain.auth.model.response.EstimatorInfoGetRes;
import com.example.api.domain.auth.model.response.SeekerInfoGetRes;
import com.example.api.domain.interview_schedule.model.request.InterviewScheduleReq;
import com.example.api.domain.interview_schedule.model.request.InterviewScheduleUpdateReq;
import com.example.api.domain.interview_schedule.model.request.ReScheduleReq;
import com.example.api.domain.interview_schedule.model.response.InterviewScheduleRes;
import com.example.api.domain.interview_schedule.model.response.ReScheduleRes;
import com.example.api.domain.interview_schedule.model.response.TimeInfoRes;
import com.example.api.global.common.exception.BaseException;
import com.example.api.global.common.responses.BaseResponseMessage;
import com.example.api.global.security.CustomUserDetails;
import com.example.api.global.utils.email.service.EmailSendEstimator;
import com.example.common.domain.announcement.model.entity.Announcement;
import com.example.common.domain.announcement.repository.AnnouncementRepository;
import com.example.common.domain.auth.model.entity.Estimator;
import com.example.common.domain.auth.model.entity.Recruiter;
import com.example.common.domain.auth.model.entity.Seeker;
import com.example.common.domain.auth.repository.EstimatorRepository;
import com.example.common.domain.auth.repository.RecruiterRepository;
import com.example.common.domain.auth.repository.SeekerRepository;
import com.example.common.domain.company.model.entity.Company;
import com.example.common.domain.company.repository.CompanyRepository;
import com.example.common.domain.interview_schedule.model.entity.InterviewParticipate;
import com.example.common.domain.interview_schedule.model.entity.InterviewSchedule;
import com.example.common.domain.interview_schedule.model.entity.ReSchedule;
import com.example.common.domain.interview_schedule.model.entity.Team;
import com.example.common.domain.interview_schedule.repository.InterviewParticipateRepository;
import com.example.common.domain.interview_schedule.repository.InterviewScheduleRepository;
import com.example.common.domain.interview_schedule.repository.ReScheduleRepository;
import com.example.common.domain.interview_schedule.repository.TeamRepository;
import com.example.common.domain.interview_schedule.repository.querydsl.InterviewScheduleDslRepository;
import com.example.common.domain.resume.repository.ResumeRepository;
import com.example.common.domain.total_process.model.entity.TotalProcess;
import com.example.common.domain.total_process.repository.TotalProcessRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class InterviewScheduleService {
    private final PasswordEncoder passwordEncoder;
    private final InterviewScheduleRepository interviewScheduleRepository;
    private final InterviewScheduleDslRepository interviewScheduleDslRepository;
    private final SeekerRepository seekerRepository;
    private final InterviewParticipateRepository interviewParticipateRepository;
    private final EstimatorRepository estimatorRepository;
    private final AnnouncementRepository announcementRepository;
    private final TeamRepository teamRepository;
    private final RecruiterRepository recruiterRepository;
    private final TotalProcessRepository totalProcessRepository;
    private final ReScheduleRepository reScheduleRepository;
    private final CompanyRepository companyRepository;
    private final EmailSendEstimator emailSendEstimator;
    @Transactional
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
                .interviewNum(dto.getInterviewNum())
                .uuid(uuid)
                .careerBase(dto.getCareerBase())
                .recruiter(recruiter)
                .announcement(announcement)
                .team(team)
                .build();
        interviewScheduleRepository.save(interviewSchedule);

        for(Long seekerIdx : dto.getSeekerList()) {
            for(String estimatorEmail : dto.getEstimatorList()) {
                String[] parts = estimatorEmail.split("-");
                String name = parts[0].trim();
                String email = parts[1].trim();
                Optional<Estimator> resultEstimator = estimatorRepository.findByEstimatorEmail(email);
                Estimator estimator = null;
                if (resultEstimator.isPresent()) {
                    estimator = resultEstimator.get();
                } else {
                    String estimatorPassword = UUID.randomUUID().toString();
                    String encodedPassword = passwordEncoder.encode(estimatorPassword);
                    boolean isMatch = passwordEncoder.matches(estimatorPassword, encodedPassword);
                    estimator = Estimator.builder()
                            .role("ROLE_ESTIMATOR")
                            .name(name)
                            .email(email)
                            .rawPassword(estimatorPassword)
                            .password(encodedPassword)
                            .emailAuth(true)
                            .build();
                    estimatorRepository.save(estimator);
                    emailSendEstimator.sendEstimatorSchedule(estimatorPassword, estimator, interviewSchedule);
                }
                InterviewParticipate interviewParticipate = interviewParticipateRepository.save(InterviewParticipate.builder()
                        .seeker(seekerRepository.findBySeekerIdx(seekerIdx).orElseThrow(() -> new BaseException(BaseResponseMessage.MEMBER_NOT_FOUND)))
                        .estimator(estimator)
                        .status(true)
                        .team(team)
                        .interviewSchedule(interviewSchedule)
                        .build());
                interviewParticipateRepository.save(interviewParticipate);
            }
        }

        List<SeekerInfoGetRes> seekerInfoGetResList = new ArrayList<>();

        for(Long seekerIdx : dto.getSeekerList()) {
            Seeker seeker = seekerRepository.findBySeekerIdx(seekerIdx).orElseThrow(() -> new BaseException(BaseResponseMessage.MEMBER_NOT_FOUND));
            seekerInfoGetResList.add(SeekerInfoGetRes.builder()
                            .idx(seekerIdx)
                            .email(seeker.getEmail())
                            .name(seeker.getName())
                            .build());
        }

        Company company = companyRepository.findByRecruiterIdx(customUserDetails.getIdx()).orElseThrow(() -> new BaseException(BaseResponseMessage.COMPANY_NOT_FOUND));

        return InterviewScheduleRes.builder()
                .idx(interviewSchedule.getIdx())
                .isOnline(interviewSchedule.getIsOnline())
                .interviewDate(interviewSchedule.getInterviewDate())
                .interviewEnd(interviewSchedule.getInterviewEnd())
                .interviewStart(interviewSchedule.getInterviewStart())
                .interviewNum(interviewSchedule.getInterviewNum())
                .uuid(interviewSchedule.getUuid())
                .seekerList(seekerInfoGetResList)
                .careerBase(interviewSchedule.getCareerBase())
                .companyName(company.getName())
                .announcementTitle(announcement.getTitle())
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

    @Transactional
    public Page<InterviewScheduleRes> readAll(String careerBase, Long idx, Integer interviewNum, Integer page, CustomUserDetails customUserDetails) throws BaseException {
        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "idx"));
        Page<InterviewSchedule> result;

        if (careerBase.equals("전체")) {
            result = interviewScheduleDslRepository.findByAnnouncementIdx(idx, pageable);
        } else {
            result = interviewScheduleDslRepository.findByCareerBaseAndAnnouncementIdxAndInterviewNum(careerBase, idx, interviewNum, pageable);
        }

        List<InterviewScheduleRes> interviewScheduleList = new ArrayList<>();

        Company company = companyRepository.findByRecruiterIdx(customUserDetails.getIdx()).orElseThrow(() -> new BaseException(BaseResponseMessage.COMPANY_NOT_FOUND));
        Announcement announcement = announcementRepository.findByAnnounceIdx(idx)
                .orElseThrow(() -> new BaseException(BaseResponseMessage.ANNOUNCEMENT_SEARCH_FAIL));

        if(!result.isEmpty()) {
            for (InterviewSchedule interviewSchedule : result) {
                List<SeekerInfoGetRes> seekerInfoGetResList = new ArrayList<>();
                List<EstimatorInfoGetRes> estimatorInfoGetResList = new ArrayList<>();
                List<InterviewParticipate> participateResult = interviewParticipateRepository.findByInterviewScheduleIdxAndStatus(interviewSchedule.getIdx(), true).orElseThrow(() ->
                        new BaseException(BaseResponseMessage.INTERVIEW_PARTICIPATE_NOT_FOUND));
                Integer count = interviewScheduleRepository.countInterviewScheduleByAnnouncementIdx(interviewSchedule.getAnnouncement().getIdx());
                for(InterviewParticipate interviewParticipate : participateResult) {
                    Seeker seeker = seekerRepository.findBySeekerIdx(interviewParticipate.getSeeker().getIdx()).orElseThrow(()->new BaseException(BaseResponseMessage.MEMBER_NOT_FOUND));
                    Estimator estimator = estimatorRepository.findByEstimatorIdx(interviewParticipate.getEstimator().getIdx()).orElseThrow(()->new BaseException(BaseResponseMessage.ESTIMATOR_NOT_FOUND));
                    seekerInfoGetResList.add(SeekerInfoGetRes.builder()
                            .idx(interviewParticipate.getSeeker().getIdx())
                            .name(seeker.getName())
                            .email(seeker.getEmail())
                            .build());

                    estimatorInfoGetResList.add(EstimatorInfoGetRes.builder()
                            .idx(interviewParticipate.getEstimator().getIdx())
                            .name(estimator.getName())
                            .email(estimator.getEmail())
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
                        .uuid(interviewSchedule.getUuid())
                        .careerBase(interviewSchedule.getCareerBase())
                        .estimatorList(estimatorInfoGetResList)
                        .companyName(company.getName())
                        .announcementTitle(announcement.getTitle())
                        .countInterviewSchedule(count)
                        .build());
            }
        }
        return new PageImpl<>(interviewScheduleList, pageable, result.getTotalElements());
    }

    public InterviewScheduleRes read(Long interviewScheduleIdx) throws BaseException {
        InterviewSchedule result = interviewScheduleRepository.findByInterviewScheduleIdx(interviewScheduleIdx).orElseThrow(() ->
                new BaseException(BaseResponseMessage.INTERVIEW_SCHEDULE_NOT_FOUND));

        List<InterviewParticipate> participateResult = interviewParticipateRepository.findByInterviewScheduleIdxAndStatus(interviewScheduleIdx, true).orElseThrow(() ->
                new BaseException(BaseResponseMessage.INTERVIEW_PARTICIPATE_NOT_FOUND));

        List<EstimatorInfoGetRes> estimatorInfoGetResList = new ArrayList<>();
        List<SeekerInfoGetRes> seekerInfoGetResList = new ArrayList<>();

        // estimator 정보 담기
        for (InterviewParticipate interviewParticipate : participateResult) {
            Long estimatorIdx = interviewParticipate.getEstimator().getIdx();

            // 이미 해당 idx를 가진 EstimatorInfoGetRes 객체가 있는지 확인
            boolean exists = estimatorInfoGetResList.stream()
                    .anyMatch(estimatorInfo -> estimatorInfo.getIdx().equals(estimatorIdx));

            // 해당 idx가 없으면 새로운 EstimatorInfoGetRes 객체 추가
            if (!exists) {
                estimatorInfoGetResList.add(EstimatorInfoGetRes.builder()
                        .idx(estimatorIdx)
                        .email(interviewParticipate.getEstimator().getEmail())
                        .name(interviewParticipate.getEstimator().getName())
                        .build());
            }
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
                .interviewNum(result.getInterviewNum())
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

        ReSchedule reSchedule = reScheduleRepository.findByReScheduleIdx(dto.getReScheduleIdx())
                .orElseThrow(() -> new BaseException(BaseResponseMessage.RESCHEDULE_NOT_FOUND));

        reSchedule.setStatus(true);
        reScheduleRepository.save(reSchedule);
    }

    public Integer getTotalInterviewSchedule(String careerBase, Long idx){
        Optional<List<InterviewSchedule>> result;
        if(careerBase.equals("전체")) {
            result = interviewScheduleRepository.findByAnnouncementIdx(idx);
        } else {
            result = interviewScheduleRepository.findByCareerBaseAndAnnouncementIdx(careerBase, idx);
        }

        return result.get().size();
    }

    public ReScheduleRes createReSchedule(CustomUserDetails customUserDetails, ReScheduleReq dto) throws BaseException {
        InterviewSchedule interviewSchedule = interviewScheduleRepository.findByInterviewScheduleIdx(dto.getInterviewScheduleIdx())
                .orElseThrow(() -> new BaseException(BaseResponseMessage.INTERVIEW_SCHEDULE_NOT_FOUND));
        Seeker seeker = seekerRepository.findBySeekerIdx(customUserDetails.getIdx()).orElseThrow(() -> new BaseException(BaseResponseMessage.MEMBER_NOT_FOUND));

        ReSchedule reSchedule = reScheduleRepository.save(ReSchedule.builder()
                .interviewStart(dto.getInterviewStart())
                .interviewEnd(dto.getInterviewEnd())
                .status(false)
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

    public List<ReScheduleRes> readAllReSchedule(Long announcementIdx, Integer pageNum) throws BaseException {
        Pageable pageable = PageRequest.of(pageNum, 10, Sort.by(Sort.Direction.DESC, "idx"));
        Page<InterviewSchedule> result = interviewScheduleDslRepository.findByAnnouncementIdx(announcementIdx, pageable);
        List<ReScheduleRes> reScheduleResList = new ArrayList<>();

        for(InterviewSchedule interviewSchedule : result) {
            List<ReSchedule> reScheduleList = reScheduleRepository.findAllByInterviewScheduleIdx(interviewSchedule.getIdx());
            if(reScheduleList == null) {
                continue;
            }

            for(ReSchedule reSchedule : reScheduleList) {
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
                                    .interviewNum(interviewSchedule.getInterviewNum())
                                    .teamIdx(interviewSchedule.getTeam().getIdx())
                                    .uuid(interviewSchedule.getUuid())
                                    .build())
                            .seekerInfoGetRes(SeekerInfoGetRes.builder()
                                    .idx(seeker.getIdx())
                                    .email(seeker.getEmail())
                                    .name(seeker.getName())
                                    .build())
                            .status(reSchedule.getStatus())
                            .build());
                }
            }



        }

        return reScheduleResList;
    }

    public Integer getTotalReSchedule(Long announcementIdx) throws BaseException {
        List<InterviewSchedule> result = interviewScheduleRepository.findByAnnouncementIdx(announcementIdx).orElseThrow(() -> new BaseException(BaseResponseMessage.INTERVIEW_SCHEDULE_NOT_FOUND));
        List<ReScheduleRes> reScheduleResList = new ArrayList<>();

        for(InterviewSchedule interviewSchedule : result) {
            List<ReSchedule> reScheduleList = reScheduleRepository.findAllByInterviewScheduleIdx(interviewSchedule.getIdx());
            if(reScheduleList == null) {
                continue;
            }

            for(ReSchedule reSchedule : reScheduleList) {
                reScheduleResList.add(ReScheduleRes.builder()
                        .idx(reSchedule.getIdx())
                        .build());
            }
        }

        return reScheduleResList.size();
    }

    public List<SeekerInfoGetRes> getSeekerList(Long announcementIdx, Integer interviewNum) throws BaseException {
        // 서류 합격자 resume(seeker info 포함)
        System.out.println("@@@@@@@@@@@@@@"+ interviewNum);
        List<TotalProcess> getResumeResult;
        if(interviewNum == 1) {
            getResumeResult = totalProcessRepository.findByAnnouncementIdxAndResumeResult(announcementIdx, true);
        } else {
            getResumeResult = totalProcessRepository.findByAnnouncementIdxAndResumeResultAndInterviewOneResult(announcementIdx, true, true);
        }

        for(TotalProcess totalProcess : getResumeResult) {
            System.out.println("????????????"+totalProcess.getSeeker().getName());
        }

        // interview_participate(면접일정이 잡힌 seeker list) 조회
        Optional<List<InterviewSchedule>> interviewScheduleResult = interviewScheduleRepository.findByAnnouncementIdxAndInterviewNum(announcementIdx, interviewNum);
        List<Long> interviewParticipateSeeker = new ArrayList<>();

        for(InterviewSchedule interviewSchedule : interviewScheduleResult.get()) {
            Optional<List<InterviewParticipate>> interviewParticipate = interviewParticipateRepository.findByInterviewScheduleIdx(interviewSchedule.getIdx());

            if(interviewParticipate.isPresent()) {
                for(InterviewParticipate interviewParticipateResult : interviewParticipate.get()) {
                    if(!interviewParticipateSeeker.contains(interviewParticipateResult.getSeeker().getIdx())){
                        interviewParticipateSeeker.add(interviewParticipateResult.getSeeker().getIdx());
                    }
                }
            }
        }

        // getResumeResult => 합격자들 resume
        // interviewParticipaateSeeker => 면접 일정이 잡힌 seekerIdx List

        List<SeekerInfoGetRes> seekerInfoGetResList = new ArrayList<>();
        for(TotalProcess resumeResult : getResumeResult) {
            if(!interviewParticipateSeeker.contains(resumeResult.getSeeker().getIdx())) {
                Seeker seeker = seekerRepository.findBySeekerIdx(resumeResult.getSeeker().getIdx()).orElseThrow(() -> new BaseException(BaseResponseMessage.MEMBER_NOT_FOUND));

                seekerInfoGetResList.add(SeekerInfoGetRes.builder()
                        .idx(seeker.getIdx())
                        .name(seeker.getName())
                        .email(seeker.getEmail())
                        .build());
            }
        }

        return seekerInfoGetResList;
    }

    public List<TimeInfoRes> getAvailableTimes(String interviewDate, Long teamIdx, Long announcementIdx) {

        Optional<List<InterviewSchedule>> interviewScheduleList = interviewScheduleRepository.findByInterviewDateAndTeamIdxAndAnnouncementIdx(interviewDate, teamIdx, announcementIdx);

        List<TimeInfoRes> timeInfoResList = new ArrayList<>();

        for(InterviewSchedule interviewSchedule : interviewScheduleList.get()) {
            timeInfoResList.add(TimeInfoRes.builder()
                    .interviewStart(interviewSchedule.getInterviewStart())
                    .interviewEnd(interviewSchedule.getInterviewEnd())
                    .build());
        }
        return timeInfoResList;
    }
}