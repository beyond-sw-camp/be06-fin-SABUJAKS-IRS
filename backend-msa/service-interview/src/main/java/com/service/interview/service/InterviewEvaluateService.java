package com.service.interview.service;

import com.service.common.base.BaseException;
import com.service.common.base.BaseResponseMessage;
import com.service.common.dto.feign.ReadAnnouncementRes;
import com.service.common.dto.feign.ReadEstimatorRes;
import com.service.common.dto.feign.ReadSeekerRes;
import com.service.common.dto.feign.ReadSubmissionResumeRes;
import com.service.common.dto.request.interview.CreateInterviewEvaluateFormReq;
import com.service.common.dto.request.interview.CreateInterviewEvaluateReq;
import com.service.common.dto.response.interview.*;
import com.service.interview.communication.InterviewFeignClient;
import com.service.interview.entity.*;
import com.service.interview.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class InterviewEvaluateService {
    private final InterviewEvaluateRepository interviewEvaluateRepository;
    private final InterviewEvaluateFormRepository interviewEvaluateFormRepository;
    private final InterviewParticipateRepository interviewParticipateRepository;
    private final InterviewEvaluateResultRepository interviewEvaluateResultRepository;
    private final InterviewScheduleRepository interviewScheduleRepository;
    private final InterviewFeignClient interviewFeignClient;

    public CreateInterviewEvaluateFormRes createForm (Long memberIdx, String memberEmail, String memberRole, CreateInterviewEvaluateFormReq dto) throws BaseException {
        if(Objects.equals(memberRole, "ROLE_RECRUITER") && Objects.equals(interviewFeignClient.readAnnouncement(dto.getAnnouncementIdx()).getRecruiterEmail(), memberEmail)) {
            throw new BaseException(BaseResponseMessage.INTERVIEW_EVALUATE_CREATE_FORM_FAIL_INVALID_REQUEST);
        }
        Optional<InterviewEvaluateForm> result = interviewEvaluateFormRepository.findByAnnouncementIdx(dto.getAnnouncementIdx());
        if(result.isEmpty()){
            InterviewEvaluateForm interviewEvaluateForm = InterviewEvaluateForm.builder()
                    .announcementIdx(dto.getAnnouncementIdx())
                    .announcementUUID(dto.getAnnouncementUUID())
                    .q1(dto.getQ1())
                    .q2(dto.getQ2())
                    .q3(dto.getQ3())
                    .q4(dto.getQ4())
                    .q5(dto.getQ5())
                    .q6(dto.getQ6())
                    .q7(dto.getQ7())
                    .q8(dto.getQ8())
                    .q9(dto.getQ9())
                    .q10(dto.getQ10())
                    .build();
            interviewEvaluateFormRepository.save(interviewEvaluateForm);
            return CreateInterviewEvaluateFormRes.builder()
                    .idx(interviewEvaluateForm.getIdx())
                    .build();
        } else {
            InterviewEvaluateForm interviewEvaluateForm = result.get();
            interviewEvaluateForm.setQ1(dto.getQ1());
            interviewEvaluateForm.setQ2(dto.getQ2());
            interviewEvaluateForm.setQ3(dto.getQ3());
            interviewEvaluateForm.setQ4(dto.getQ4());
            interviewEvaluateForm.setQ5(dto.getQ5());
            interviewEvaluateForm.setQ6(dto.getQ6());
            interviewEvaluateForm.setQ7(dto.getQ7());
            interviewEvaluateForm.setQ8(dto.getQ8());
            interviewEvaluateForm.setQ9(dto.getQ9());
            interviewEvaluateForm.setQ10(dto.getQ10());
            interviewEvaluateFormRepository.save(interviewEvaluateForm);
            return CreateInterviewEvaluateFormRes.builder().idx(interviewEvaluateForm.getIdx()).build();
        }
    }

    public ReadInterviewEvaluateFormRes readForm(Long memberIdx, String memberEmail, String memberRole, String announcementUUID, String interviewScheduleUUID) throws  BaseException {
        if(Objects.equals(memberRole, "ROLE_RECRUITER")){
            InterviewEvaluateForm interviewEvaluateForm = interviewEvaluateFormRepository.findByAnnouncementUUID(announcementUUID)
                    .orElseThrow(() -> new BaseException(BaseResponseMessage.INTERVIEW_EVALUATE_SEARCH_FORM_FAIL_IS_NOT_EXIST));
            if(Objects.equals(memberRole, "ROLE_RECRUITER") && Objects.equals(interviewFeignClient.readAnnouncement(interviewEvaluateForm.getAnnouncementIdx()).getRecruiterEmail(), memberEmail)) {
                throw new BaseException(BaseResponseMessage.INTERVIEW_EVALUATE_SEARCH_FORM_FAIL_INVALID_ACCESS);
            }
            return ReadInterviewEvaluateFormRes.builder()
                    .q1(interviewEvaluateForm.getQ1())
                    .q2(interviewEvaluateForm.getQ2())
                    .q3(interviewEvaluateForm.getQ3())
                    .q4(interviewEvaluateForm.getQ4())
                    .q5(interviewEvaluateForm.getQ5())
                    .q6(interviewEvaluateForm.getQ6())
                    .q7(interviewEvaluateForm.getQ7())
                    .q8(interviewEvaluateForm.getQ8())
                    .q9(interviewEvaluateForm.getQ9())
                    .q10(interviewEvaluateForm.getQ10())
                    .build();
        } else if(Objects.equals(memberRole, "ROLE_ESTIMATOR")) {
            if(interviewParticipateRepository.findFirstByEstimatorEmailAndInterviewScheduleUUID(memberEmail, interviewScheduleUUID).isEmpty()){
                throw new BaseException(BaseResponseMessage.INTERVIEW_EVALUATE_SEARCH_FORM_FAIL_INVALID_ACCESS);
            }
            InterviewEvaluateForm interviewEvaluateForm = interviewEvaluateFormRepository.findByAnnouncementUUID(announcementUUID)
                    .orElseThrow(() -> new BaseException(BaseResponseMessage.INTERVIEW_EVALUATE_SEARCH_FORM_FAIL_IS_NOT_EXIST));
            return ReadInterviewEvaluateFormRes.builder()
                    .q1(interviewEvaluateForm.getQ1())
                    .q2(interviewEvaluateForm.getQ2())
                    .q3(interviewEvaluateForm.getQ3())
                    .q4(interviewEvaluateForm.getQ4())
                    .q5(interviewEvaluateForm.getQ5())
                    .q6(interviewEvaluateForm.getQ6())
                    .q7(interviewEvaluateForm.getQ7())
                    .q8(interviewEvaluateForm.getQ8())
                    .q9(interviewEvaluateForm.getQ9())
                    .q10(interviewEvaluateForm.getQ10())
                    .build();
        } else {
            throw new BaseException(BaseResponseMessage.INTERVIEW_EVALUATE_SEARCH_FORM_FAIL_INVALID_ACCESS);
        }
    }

    @Transactional
    public ReadAllSubmissionResumeRes readAllSubmissionResume(Long memberIdx, String memberEmail, String memberRole, String announcementUUID, String interviewScheduleUUID) throws BaseException{
        List<InterviewParticipate> interviewParticipateList = interviewParticipateRepository
                .findAllByEstimatorIdxAndInterviewScheduleUUID(memberEmail, interviewScheduleUUID)
                .orElseThrow(() -> new BaseException(BaseResponseMessage.INTERVIEW_EVALUATE_SEARCH_FORM_FAIL_INVALID_ACCESS));
        Map<Long, ReadSubmissionResumeRes> readSubmissionResumeResMap = new HashMap();
        for(InterviewParticipate interviewParticipate:interviewParticipateList){
            ReadSubmissionResumeRes readSubmissionResumeRes = interviewFeignClient.readResume(interviewParticipate.getInterviewSchedule().getAnnouncementIdx(), interviewParticipate.getSeekerIdx());
            readSubmissionResumeResMap.put(interviewParticipate.getSeekerIdx(), readSubmissionResumeRes);
        }
        return ReadAllSubmissionResumeRes.builder()
                .readAllSubmissionResumeResMap(readSubmissionResumeResMap)
                .build();
    }

    // TODO: 평가생성 클라이언트 수정
    @Transactional
    public CreateInterviewEvaluateRes createEvaluate(Long memberIdx, String memberEmail, String memberRole, CreateInterviewEvaluateReq dto) throws BaseException{
        InterviewParticipate interviewParticipate = interviewParticipateRepository
                .findBySeekerIdxAndEstimatorEmailAndInterviewScheduleUUID(dto.getMemberIdx(), memberEmail, dto.getVideoInterviewUUID())
                .orElseThrow(() -> new BaseException(BaseResponseMessage.INTERVIEW_EVALUATE_CREATE_FAIL_NOT_FOUND_SCHEDULE));
        InterviewEvaluateForm interviewEvaluateForm = interviewEvaluateFormRepository.findByAnnouncementUUID(dto.getAnnouncementUUID())
                .orElseThrow(() -> new BaseException(BaseResponseMessage.INTERVIEW_EVALUATE_CREATE_FAIL_INVALID_FORM));
        Optional<InterviewEvaluate> result = interviewEvaluateRepository.findByInterviewParticipateIdx(interviewParticipate.getIdx());
        if(result.isPresent()){
            InterviewEvaluate interviewEvaluate = result.get();
            InterviewEvaluateResult interviewEvaluateResult = InterviewEvaluateResult.builder()
                    .idx(interviewEvaluate.getInterviewEvaluateResult().getIdx())
                    .r1(dto.getScores().get(1))
                    .r2(dto.getScores().get(2))
                    .r3(dto.getScores().get(3))
                    .r4(dto.getScores().get(4))
                    .r5(dto.getScores().get(5))
                    .r6(dto.getScores().get(6))
                    .r7(dto.getScores().get(7))
                    .r8(dto.getScores().get(8))
                    .r9(dto.getScores().get(9))
                    .r10(dto.getScores().get(10))
                    .build();
            interviewEvaluateResultRepository.save(interviewEvaluateResult);
            InterviewEvaluate newInterviewEvaluate = InterviewEvaluate.builder()
                    .idx(interviewEvaluate.getIdx())
                    .totalScore(dto.getTotalScore())
                    .comments(dto.getComments())
                    .interviewEvaluateForm(interviewEvaluateForm)
                    .interviewParticipate(interviewParticipate)
                    .interviewEvaluateResult(interviewEvaluateResult)
                    .build();
            interviewEvaluateRepository.save(newInterviewEvaluate);
            return CreateInterviewEvaluateRes.builder().idx(newInterviewEvaluate.getIdx()).build();
        } else {
            InterviewEvaluateResult interviewEvaluateResult = InterviewEvaluateResult.builder()
                    .r1(dto.getScores().get(1))
                    .r2(dto.getScores().get(2))
                    .r3(dto.getScores().get(3))
                    .r4(dto.getScores().get(4))
                    .r5(dto.getScores().get(5))
                    .r6(dto.getScores().get(6))
                    .r7(dto.getScores().get(7))
                    .r8(dto.getScores().get(8))
                    .r9(dto.getScores().get(9))
                    .r10(dto.getScores().get(10))
                    .build();
            interviewEvaluateResultRepository.save(interviewEvaluateResult);
            InterviewEvaluate interviewEvaluate = InterviewEvaluate.builder()
                    .totalScore(dto.getTotalScore())
                    .comments(dto.getComments())
                    .interviewEvaluateForm(interviewEvaluateForm)
                    .interviewParticipate(interviewParticipate)
                    .interviewEvaluateResult(interviewEvaluateResult)
                    .build();
            interviewEvaluateRepository.save(interviewEvaluate);
            return CreateInterviewEvaluateRes.builder().idx(interviewEvaluate.getIdx()).build();
        }
    }

    public ReadAllInterviewEvaluateRes readAllEvaluate(Long memberIdx, String memberEmail, String memberRole, Long announcementIdx, Integer interviewNum) throws BaseException {
        ReadAnnouncementRes ReadAnnouncementRes = interviewFeignClient.readAnnouncement(announcementIdx);
        if (!Objects.equals(ReadAnnouncementRes.getRecruiterEmail(), memberEmail)) {
            throw new BaseException(BaseResponseMessage.ANNOUNCEMENT_SEARCH_FAIL_INVALID_REQUEST);
        }
        List<InterviewSchedule> interviewScheduleList = interviewScheduleRepository.findAllByAnnouncementIdxAndInterviewNum(announcementIdx, interviewNum)
        .orElseThrow(() -> new BaseException(BaseResponseMessage.INTERVIEW_SCHEDULE_NOT_FOUND));
        Map<Long, List<ReadInterviewEvaluateRes>> interviewEvaluateReadAllResMap = new HashMap<>();
        for(InterviewSchedule interviewSchedule : interviewScheduleList){
            List<InterviewParticipate> interviewParticipateList = interviewParticipateRepository.findByInterviewScheduleIdx(interviewSchedule.getIdx())
                    .orElseThrow(() -> new BaseException(BaseResponseMessage.INTERVIEW_PARTICIPATE_NOT_FOUND));
            for(InterviewParticipate interviewParticipate : interviewParticipateList){
                List<InterviewEvaluate> interviewEvaluateList = interviewEvaluateRepository
                        .findAllByInterviewParticipateIdxAndSeekerIdx(interviewParticipate.getIdx(), interviewParticipate.getSeekerIdx())
                        .orElseThrow(() -> new BaseException(BaseResponseMessage.INTERVIEW_EVALUATE_READ_ALL_FAIL));
                for(InterviewEvaluate interviewEvaluate : interviewEvaluateList){
                    ReadInterviewEvaluateResultRes readInterviewEvaluateResultRes = ReadInterviewEvaluateResultRes.builder()
                            .r1(interviewEvaluate.getInterviewEvaluateResult().getR1())
                            .r2(interviewEvaluate.getInterviewEvaluateResult().getR2())
                            .r3(interviewEvaluate.getInterviewEvaluateResult().getR3())
                            .r4(interviewEvaluate.getInterviewEvaluateResult().getR4())
                            .r5(interviewEvaluate.getInterviewEvaluateResult().getR5())
                            .r6(interviewEvaluate.getInterviewEvaluateResult().getR6())
                            .r7(interviewEvaluate.getInterviewEvaluateResult().getR7())
                            .r8(interviewEvaluate.getInterviewEvaluateResult().getR8())
                            .r9(interviewEvaluate.getInterviewEvaluateResult().getR9())
                            .r10(interviewEvaluate.getInterviewEvaluateResult().getR10())
                            .build();
                    ReadInterviewEvaluateFormRes readInterviewEvaluateFormRes = ReadInterviewEvaluateFormRes.builder()
                            .q1(interviewEvaluate.getInterviewEvaluateForm().getQ1())
                            .q2(interviewEvaluate.getInterviewEvaluateForm().getQ2())
                            .q3(interviewEvaluate.getInterviewEvaluateForm().getQ3())
                            .q4(interviewEvaluate.getInterviewEvaluateForm().getQ4())
                            .q5(interviewEvaluate.getInterviewEvaluateForm().getQ5())
                            .q6(interviewEvaluate.getInterviewEvaluateForm().getQ6())
                            .q7(interviewEvaluate.getInterviewEvaluateForm().getQ7())
                            .q8(interviewEvaluate.getInterviewEvaluateForm().getQ8())
                            .q9(interviewEvaluate.getInterviewEvaluateForm().getQ9())
                            .q10(interviewEvaluate.getInterviewEvaluateForm().getQ10())
                            .build();
                    ReadSeekerRes readSeekerRes = interviewFeignClient.readSeeker(interviewParticipate.getSeekerIdx());
                    ReadEstimatorRes readEstimatorRes = interviewFeignClient.readEstimator(interviewParticipate.getEstimatorEmail());
                    ReadInterviewEvaluateRes interviewEvaluateReadRes = ReadInterviewEvaluateRes.builder()
                            .seekerName(readSeekerRes.getName())
                            .seekerEmail(readSeekerRes.getEmail())
                            .estimatorEmail(readEstimatorRes.getEmail())
                            .estimatorName(readEstimatorRes.getName())
                            .totalScore(interviewEvaluate.getTotalScore())
                            .comments(interviewEvaluate.getComments())
                            .readInterviewEvaluateFormRes(readInterviewEvaluateFormRes)
                            .readInterviewEvaluateResultRes(readInterviewEvaluateResultRes)
                            .build();
                    if (interviewEvaluateReadAllResMap.containsKey(interviewParticipate.getSeekerIdx())) {
                        interviewEvaluateReadAllResMap.get(interviewParticipate.getSeekerIdx()).add(interviewEvaluateReadRes);
                    } else {
                        List<ReadInterviewEvaluateRes> interviewEvaluateReadResList = new ArrayList<>();
                        interviewEvaluateReadResList.add(interviewEvaluateReadRes);
                        interviewEvaluateReadAllResMap.put(interviewParticipate.getSeekerIdx(), interviewEvaluateReadResList);
                    }
                }
            }
        }
        return ReadAllInterviewEvaluateRes.builder().interviewEvaluateReadAllResMap(interviewEvaluateReadAllResMap).build();
    }
}
