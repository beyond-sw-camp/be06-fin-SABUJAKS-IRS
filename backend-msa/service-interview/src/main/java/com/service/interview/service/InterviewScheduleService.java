package com.service.interview.service;

import com.service.common.base.BaseException;
import com.service.common.base.BaseResponseMessage;
import com.service.common.dto.feign.ReadAnnouncementRes;
import com.service.common.dto.feign.ReadEstimatorRes;
import com.service.common.dto.feign.ReadSeekerRes;
import com.service.common.dto.kafka.CreateEstimatorMsg;
import com.service.common.dto.response.interview.*;
import com.service.interview.communication.InterviewFeignClient;
import com.service.interview.communication.InterviewKafkaProducer;
import com.service.interview.entity.InterviewParticipate;
import com.service.interview.entity.InterviewReSchedule;
import com.service.interview.entity.InterviewSchedule;
import com.service.common.dto.request.interview.CreateAllInterviewReScheduleReq;
import com.service.common.dto.request.interview.CreateInterviewReScheduleReq;
import com.service.common.dto.request.interview.CreateInterviewScheduleReq;
import com.service.interview.entity.InterviewTeam;
import com.service.interview.repository.InterviewParticipateRepository;
import com.service.interview.repository.InterviewScheduleRepository;
import com.service.interview.repository.IntervieReScheduleRepository;
import com.service.interview.repository.InterviewTeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class InterviewScheduleService {
    private final PasswordEncoder passwordEncoder;
    private final InterviewScheduleRepository interviewScheduleRepository;
//    private final SeekerRepository seekerRepository;
    private final InterviewParticipateRepository interviewParticipateRepository;
//    private final EstimatorRepository estimatorRepository;
//    private final AnnouncementRepository announcementRepository;
//    private final TeamRepository teamRepository;
//    private final RecruiterRepository recruiterRepository;
//    private final TotalProcessRepository totalProcessRepository;
    private final IntervieReScheduleRepository intervieReScheduleRepository;
//    private final ResumeRepository resumeRepository;
//    private final CompanyRepository companyRepository;
    private final InterviewTeamRepository interviewTeamRepository;
    private final InterviewFeignClient interviewFeignClient;
    private final InterviewKafkaProducer interviewKafkaProducer;

    @Transactional
    public CreateInterviewScheduleRes create(Long memberIdx, String memberEmail, String memberRole, CreateInterviewScheduleReq dto) throws BaseException {
        ReadAnnouncementRes readAnnouncementRes = interviewFeignClient.readAnnouncement(memberIdx);
        if(!("ROLE_RECRUITER".equals(memberRole) & Objects.equals(readAnnouncementRes.getRecruiterEmail(), memberEmail))) {
            throw new BaseException(BaseResponseMessage.INTERVIEW_SCHEDULE_CREATE_FAIL_INVALID_REQUEST);
        }
        InterviewTeam interviewTeam = interviewTeamRepository.findByIdx(dto.getTeamIdx())
                .orElseThrow(() -> new BaseException(BaseResponseMessage.TEAM_NOT_FOUND));
        String uuid = uuidCheck(dto);
        InterviewSchedule interviewSchedule = InterviewSchedule.builder()
                .announcementIdx(dto.getAnnouncementIdx())
                .announcementUUID(readAnnouncementRes.getAnnouncementUUID())
                .recruiterIdx(memberIdx)
                .isOnline(dto.getIsOnline())
                .interviewDate(dto.getInterviewDate())
                .interviewStart(dto.getInterviewStart())
                .interviewEnd(dto.getInterviewEnd())
                .interviewNum(dto.getInterviewNum())
                .interviewScheduleUUID(uuid)
                .careerBase(dto.getCareerBase())
                .recruiterIdx(memberIdx)
                .announcementIdx(dto.getAnnouncementIdx())
                .interviewTeam(interviewTeam)
                .build();
        interviewScheduleRepository.save(interviewSchedule);
        // TODO: QWER1234 -> UUID만들고 전송 + 클라이언트 페이지
        List<ReadEstimatorRes> readEstimatorResList = new ArrayList<>();
//        List<String> estimatorPasswordList = new ArrayList<>();
        for(Long seekerIdx : dto.getSeekerList()) {
            for(String estimatorEmail : dto.getEstimatorList()) {
//                String estimatorPassword = UUID.randomUUID().toString();
                String[] parts = estimatorEmail.split("-");
                String name = parts[0].trim();
                String email = parts[1].trim();
                interviewKafkaProducer.createEstimatorMsg(CreateEstimatorMsg.builder()
                                .recruiterIdx(memberIdx)
                                .email(email)
                                .name(name)
                                .password("qwer1234")
                                .role("ROLE_ESTIMATOR")
                                .build()
                );
                ReadEstimatorRes readEstimatorRes = ReadEstimatorRes.builder()
                        .email(email)
                        .name(name)
                        .role("ROLE_ESTIMATOR")
                        .build();
                readEstimatorResList.add(readEstimatorRes);
                InterviewParticipate interviewParticipate = interviewParticipateRepository.save(InterviewParticipate.builder()
                        .estimatorEmail(email)
                        .status(true)
                        .interviewTeam(interviewTeam)
                        .interviewSchedule(interviewSchedule)
                        .build());
                interviewParticipateRepository.save(interviewParticipate);
            }
        }
        List<ReadSeekerRes> readSeekerResList = new ArrayList<>();
        for(Long seekerIdx : dto.getSeekerList()) {
            ReadSeekerRes readSeekerRes = interviewFeignClient.readSeeker(seekerIdx);
            readSeekerResList.add(readSeekerRes);
        }
        return CreateInterviewScheduleRes.builder()
                .idx(interviewSchedule.getIdx())
                .isOnline(interviewSchedule.getIsOnline())
                .interviewDate(interviewSchedule.getInterviewDate())
                .interviewEnd(interviewSchedule.getInterviewEnd())
                .interviewStart(interviewSchedule.getInterviewStart())
                .interviewNum(interviewSchedule.getInterviewNum())
                .uuid(interviewSchedule.getInterviewScheduleUUID())
                .readEstimatorResList(readEstimatorResList)
                .readSeekerResList(readSeekerResList)
                .careerBase(interviewSchedule.getCareerBase())
                .companyName(readAnnouncementRes.getCompanyName())
                .announcementTitle(readAnnouncementRes.getAnnouncementTitle())
                .build();
    }

    public List<ReadInterviewScheduleRes> readAll(Long memberIdx, String memberEmail, String memberRole, String careerBase, Long announcementIdx, Integer page) throws BaseException {
        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "idx"));
        Page<InterviewSchedule> result;
        if (careerBase.equals("전체")) {
            result = interviewScheduleRepository.findByAnnouncementIdx(announcementIdx, pageable);
        } else {
            result = interviewScheduleRepository.findByCareerBaseAndAnnouncementIdx(careerBase, announcementIdx, pageable);
        }
        List<ReadInterviewScheduleRes> interviewScheduleList = new ArrayList<>();
        ReadAnnouncementRes readAnnouncementRes = interviewFeignClient.readAnnouncement(announcementIdx);
        if(!result.isEmpty()) {
            for (InterviewSchedule interviewSchedule : result) {
                List<ReadSeekerRes> readSeekerResList = new ArrayList<>();
                List<ReadEstimatorRes> readEstimatorResList = new ArrayList<>();
                List<InterviewParticipate> participateResult = interviewParticipateRepository.findByInterviewScheduleIdxAndStatus(interviewSchedule.getIdx(), true)
                        .orElseThrow(() -> new BaseException(BaseResponseMessage.INTERVIEW_PARTICIPATE_NOT_FOUND));
                Integer count = interviewScheduleRepository.countInterviewScheduleByAnnouncementIdx(interviewSchedule.getAnnouncementIdx());
                for(InterviewParticipate interviewParticipate : participateResult) {
                    ReadSeekerRes readSeekerRes = interviewFeignClient.readSeeker(interviewParticipate.getSeekerIdx());
                    readSeekerResList.add(readSeekerRes);
                    ReadEstimatorRes readEstimatorRes = interviewFeignClient.readEstimator(interviewParticipate.getEstimatorEmail());
                    readEstimatorResList.add(readEstimatorRes);
                }
                interviewScheduleList.add(ReadInterviewScheduleRes.builder()
                        .idx(interviewSchedule.getIdx())
                        .isOnline(interviewSchedule.getIsOnline())
                        .interviewDate(interviewSchedule.getInterviewDate())
                        .interviewStart(interviewSchedule.getInterviewStart())
                        .interviewEnd(interviewSchedule.getInterviewEnd())
                        .teamIdx(interviewSchedule.getInterviewTeam().getIdx())
                        .readSeekerResList(readSeekerResList)
                        .readEstimatorResList(readEstimatorResList)
                        .uuid(interviewSchedule.getInterviewScheduleUUID())
                        .careerBase(interviewSchedule.getCareerBase())
                        .companyName(readAnnouncementRes.getCompanyName())
                        .announcementTitle(readAnnouncementRes.getAnnouncementTitle())
                        .countInterviewSchedule(count)
                        .build());
            }
        }

        return interviewScheduleList;
    }

    public ReadInterviewScheduleRes read(Long interviewScheduleIdx) throws BaseException {
        InterviewSchedule interviewSchedule = interviewScheduleRepository.findByInterviewScheduleIdx(interviewScheduleIdx).orElseThrow(() ->
                new BaseException(BaseResponseMessage.INTERVIEW_SCHEDULE_NOT_FOUND));
        List<InterviewParticipate> participateResult = interviewParticipateRepository.findByInterviewScheduleIdxAndStatus(interviewScheduleIdx, true).orElseThrow(() ->
                new BaseException(BaseResponseMessage.INTERVIEW_PARTICIPATE_NOT_FOUND));
        List<ReadEstimatorRes> readEstimatorResList = new ArrayList<>();
        List<ReadSeekerRes> readSeekerResList = new ArrayList<>();
        // estimator 정보 담기
        for (InterviewParticipate interviewParticipate : participateResult) {
            String estimatorEmail = interviewParticipate.getEstimatorEmail();
            // 이미 해당 idx를 가진 EstimatorInfoGetRes 객체가 있는지 확인
            boolean exists = readEstimatorResList.stream()
                    .anyMatch(readEstimatorRes -> readEstimatorRes.getEmail().equals(estimatorEmail));
            // 해당 idx가 없으면 새로운 EstimatorInfoGetRes 객체 추가
            if (!exists) {
                ReadEstimatorRes readEstimatorInfoRes = interviewFeignClient.readEstimator(estimatorEmail);
                readEstimatorResList.add(ReadEstimatorRes.builder()
                        .email(readEstimatorInfoRes.getEmail())
                        .name(readEstimatorInfoRes.getName())
                        .build());
            }
        }
        // seeker 정보 담기
        for(InterviewParticipate interviewParticipate : participateResult) {
            ReadSeekerRes readSeekerRes = interviewFeignClient.readSeeker(interviewParticipate.getSeekerIdx());
            readSeekerResList.add(readSeekerRes);
        }
        ReadInterviewScheduleRes readInterviewScheduleRes = ReadInterviewScheduleRes.builder()
                .idx(interviewSchedule.getIdx())
                .isOnline(interviewSchedule.getIsOnline())
                .interviewDate(interviewSchedule.getInterviewDate())
                .interviewEnd(interviewSchedule.getInterviewEnd())
                .interviewStart(interviewSchedule.getInterviewStart())
                .interviewNum(interviewSchedule.getInterviewNum())
                .teamIdx(interviewSchedule.getInterviewTeam().getIdx())
                .uuid(interviewSchedule.getInterviewScheduleUUID())
                .readSeekerResList(readSeekerResList)
                .readEstimatorResList(readEstimatorResList)
                .build();
        return readInterviewScheduleRes;
    }

    public void update(CreateAllInterviewReScheduleReq dto) throws BaseException {
        for (Long seekerIdx : dto.getSeekerList()) {
            Long interviewScheduleIdx = dto.getInterviewScheduleIdx();
            InterviewParticipate interviewParticipate = interviewParticipateRepository.findByInterviewScheduleIdxAndSeekerIdx(interviewScheduleIdx, seekerIdx)
                    .orElseThrow(() -> new BaseException(BaseResponseMessage.INTERVIEW_PARTICIPATE_NOT_FOUND));
            if (interviewParticipate != null) {
                interviewParticipate.setStatus(false);
                interviewParticipateRepository.save(interviewParticipate);
            }
        }
        InterviewReSchedule interviewReSchedule = intervieReScheduleRepository.findByReScheduleIdx(dto.getReScheduleIdx())
                .orElseThrow(() -> new BaseException(BaseResponseMessage.RESCHEDULE_NOT_FOUND));
        interviewReSchedule.setStatus(true);
        intervieReScheduleRepository.save(interviewReSchedule);
    }

    public CreateInterviewReScheduleRes createReSchedule(Long memberIdx, String memberEmail, String memberRole, CreateInterviewReScheduleReq dto) throws BaseException {
        InterviewSchedule interviewSchedule = interviewScheduleRepository.findByInterviewScheduleIdx(dto.getInterviewScheduleIdx())
                .orElseThrow(() -> new BaseException(BaseResponseMessage.INTERVIEW_SCHEDULE_NOT_FOUND));
        InterviewReSchedule interviewReSchedule = intervieReScheduleRepository.save(InterviewReSchedule.builder()
                .interviewStart(dto.getInterviewStart())
                .interviewEnd(dto.getInterviewEnd())
                .status(false)
                .interviewSchedule(interviewSchedule)
                .seekerIdx(memberIdx)
                .build());
        ReadSeekerRes readSeekerRes = interviewFeignClient.readSeeker();
        return CreateInterviewReScheduleRes.builder()
                .idx(interviewReSchedule.getIdx())
                .interviewStart(interviewReSchedule.getInterviewStart())
                .interviewEnd(interviewReSchedule.getInterviewEnd())
                .readInterviewScheduleRes(ReadInterviewScheduleRes.builder()
                        .idx(interviewReSchedule.getInterviewSchedule().getIdx())
                        .isOnline(interviewReSchedule.getInterviewSchedule().getIsOnline())
                        .interviewDate(interviewReSchedule.getInterviewSchedule().getInterviewDate())
                        .interviewStart(interviewReSchedule.getInterviewSchedule().getInterviewStart())
                        .interviewEnd(interviewReSchedule.getInterviewSchedule().getInterviewEnd())
                        .teamIdx(interviewSchedule.getInterviewTeam().getIdx())
                        .uuid(interviewSchedule.getInterviewScheduleUUID())
                        .build())
                .readSeekerRes(readSeekerRes)
                .build();
    }

    public List<ReadInterviewReScheduleRes> readAllReSchedule(Long announcementIdx, Integer pageNum) throws BaseException {
        Pageable pageable = PageRequest.of(pageNum, 10, Sort.by(Sort.Direction.DESC, "idx"));
        Page<InterviewSchedule> result = interviewScheduleRepository.findByAnnouncementIdx(announcementIdx, pageable);
        List<ReadInterviewReScheduleRes> readInterviewReScheduleResList = new ArrayList<>();
        for(InterviewSchedule interviewSchedule : result) {
            InterviewReSchedule interviewReSchedule = intervieReScheduleRepository.findByInterviewScheduleIdx(interviewSchedule.getIdx());
            if(interviewReSchedule == null) {
                continue;
            }
            ReadSeekerRes readSeekerRes = interviewFeignClient.readSeeker(interviewReSchedule.getSeekerIdx());
            if(interviewReSchedule != null) {
                readInterviewReScheduleResList.add(ReadInterviewReScheduleRes.builder()
                        .idx(interviewReSchedule.getIdx())
                        .interviewStart(interviewReSchedule.getInterviewStart())
                        .interviewEnd(interviewReSchedule.getInterviewEnd())
                        .readInterviewScheduleRes(ReadInterviewScheduleRes.builder()
                                .idx(interviewSchedule.getIdx())
                                .isOnline(interviewSchedule.getIsOnline())
                                .interviewDate(interviewSchedule.getInterviewDate())
                                .interviewStart(interviewSchedule.getInterviewStart())
                                .interviewEnd(interviewSchedule.getInterviewEnd())
                                .interviewNum(interviewSchedule.getInterviewNum())
                                .teamIdx(interviewSchedule.getInterviewTeam().getIdx())
                                .uuid(interviewSchedule.getInterviewScheduleUUID())
                                .build())
                        .readSeekerRes(readSeekerRes)
                        .status(interviewReSchedule.getStatus())
                        .build());
            }
        }
        return readInterviewReScheduleResList;
    }

//    public List<ReadSeekerRes> getSeekerList(Long announcementIdx) throws BaseException {
//        // 서류 합격자 resume(seeker info 포함)
////        TODO:
////        List<TotalProcess> getResumeResult = totalProcessRepository.findByAnnouncementIdxAndResumeResult(announcementIdx, true);
//        // interview_participate(면접일정이 잡힌 seeker list) 조회
//        Optional<List<InterviewSchedule>> interviewScheduleResult = interviewScheduleRepository.findByAnnouncementIdx(announcementIdx);
//        List<Long> interviewParticipateSeeker = new ArrayList<>();
//        for(InterviewSchedule interviewSchedule : interviewScheduleResult.get()) {
//            Optional<List<InterviewParticipate>> interviewParticipate = interviewParticipateRepository.findByInterviewScheduleIdx(interviewSchedule.getIdx());
//            if(interviewParticipate.isPresent()) {
//                for(InterviewParticipate interviewParticipateResult : interviewParticipate.get()) {
//                    if(!interviewParticipateSeeker.contains(interviewParticipateResult.getSeekerIdx())){
//                        interviewParticipateSeeker.add(interviewParticipateResult.getSeekerIdx());
//                    }
//                }
//            }
//        }
//        // TODO: TOTALPROCESS NEED
//        // getResumeResult => 합격자들 resume
//        // interviewParticipaateSeeker => 면접 일정이 잡힌 seekerIdx List
//        List<ReadSeekerRes> readSeekerResList= new ArrayList<>();
//        for(TotalProcess resumeResult : getResumeResult) {
//            if(!interviewParticipateSeeker.contains(resumeResult.getSeeker().getIdx())) {
//                Seeker seeker = seekerRepository.findBySeekerIdx(resumeResult.getSeeker().getIdx()).orElseThrow(() -> new BaseException(BaseResponseMessage.AUTH_READ_FAIL_SEEKER_INFO_NOT_FOUND));
//                readSeekerResList.add(ReadSeekerRes.builder()
//                        .idx(read)
//                        .name(seeker.getName())
//                        .email(seeker.getEmail())
//                        .build());
//            }
//        }
//        return readSeekerResList;
//    }

    public Integer getTotalScheduleSize(String careerBase, Long announcementIdx){
        Optional<List<InterviewSchedule>> result;
        if(careerBase.equals("전체")) {
            result = interviewScheduleRepository.findByAnnouncementIdx(announcementIdx);
        } else {
            result = interviewScheduleRepository.findByCareerBaseAndAnnouncementIdx(careerBase, announcementIdx);
        }
        return result.get().size();
    }

    public Integer getTotalReScheduleSize(Long announcementIdx) throws BaseException {
        List<InterviewSchedule> result = interviewScheduleRepository.findByAnnouncementIdx(announcementIdx).orElseThrow(() -> new BaseException(BaseResponseMessage.INTERVIEW_SCHEDULE_NOT_FOUND));
        List<ReadInterviewReScheduleRes> interviewReScheduleResList = new ArrayList<>();
        for(InterviewSchedule interviewSchedule : result) {
            InterviewReSchedule interviewReSchedule = intervieReScheduleRepository.findByInterviewScheduleIdx(interviewSchedule.getIdx());
            if(interviewReSchedule == null) {
                continue;
            }
            interviewReScheduleResList.add(ReadInterviewReScheduleRes.builder()
                    .idx(interviewReSchedule.getIdx())
                    .build());
        }
        return interviewReScheduleResList.size();
    }

    public List<ReadAvailableTimeRes> readAvailableTime(String interviewDate, Long teamIdx, Long announcementIdx) {
        Optional<List<InterviewSchedule>> interviewScheduleList = interviewScheduleRepository.findByInterviewDateAndTeamIdxAndAnnouncementIdx(interviewDate, teamIdx, announcementIdx);
        List<ReadAvailableTimeRes> readAvailableTimeResList = new ArrayList<>();
        for(InterviewSchedule interviewSchedule : interviewScheduleList.get()) {
            readAvailableTimeResList.add(ReadAvailableTimeRes.builder()
                    .interviewStart(interviewSchedule.getInterviewStart())
                    .interviewEnd(interviewSchedule.getInterviewEnd())
                    .build());
        }
        return readAvailableTimeResList;
    }

    public String uuidCheck(CreateInterviewScheduleReq dto) {
        String uuid = "";
        List<InterviewSchedule> result = interviewScheduleRepository.findByInterviewDate(dto.getInterviewDate());
        if(result.isEmpty()) {
            return UUID.randomUUID().toString();
        }
        for(InterviewSchedule interviewSchedule : result) {
            if((interviewSchedule.getInterviewTeam().getIdx()).equals(dto.getTeamIdx())) {
                uuid = interviewSchedule.getInterviewScheduleUUID();
            }
        }

        if(uuid.isEmpty()) {
            uuid = UUID.randomUUID().toString();
        }

        return uuid;
    }
}