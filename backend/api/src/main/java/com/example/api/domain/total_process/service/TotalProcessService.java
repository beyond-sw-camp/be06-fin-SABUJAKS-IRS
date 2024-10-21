package com.example.api.domain.total_process.service;

import com.example.api.domain.total_process.model.request.TotalProcessCreateReq;
import com.example.api.domain.total_process.model.response.TotalProcessCreateRes;
import com.example.api.domain.total_process.model.response.TotalProcessReadAllRes;
import com.example.api.global.common.exception.BaseException;
import com.example.api.global.common.responses.BaseResponseMessage;
import com.example.api.global.security.CustomUserDetails;
import com.example.common.domain.announcement.model.entity.Announcement;
import com.example.common.domain.announcement.repository.AnnouncementRepository;
import com.example.common.domain.auth.model.entity.Recruiter;
import com.example.common.domain.auth.model.entity.Seeker;
import com.example.common.domain.auth.repository.RecruiterRepository;
import com.example.common.domain.auth.repository.SeekerRepository;
import com.example.common.domain.total_process.model.entity.TotalProcess;
import com.example.common.domain.total_process.repository.TotalProcessRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TotalProcessService {
    private final AnnouncementRepository announcementRepository;
    private final SeekerRepository seekerRepository;
    private final TotalProcessRepository totalProcessRepository;
    private final RecruiterRepository recruiterRepository;

    @Transactional
    public TotalProcessCreateRes create(TotalProcessCreateReq dto, CustomUserDetails customUserDetails) throws BaseException {
        Announcement announcement = announcementRepository.findByAnnounceIdx(dto.getAnnouncementIdx())
        .orElseThrow(() -> new BaseException(BaseResponseMessage.ANNOUNCEMENT_SEARCH_FAIL_NOT_FOUND));
        if(!Objects.equals(announcement.getRecruiter().getIdx(), customUserDetails.getRecruiter().getIdx())) {
            throw new BaseException(BaseResponseMessage.ANNOUNCEMENT_SEARCH_FAIL_INVALID_REQUEST);
        }
        Seeker seeker = seekerRepository.findBySeekerIdx(dto.getSeekerIdx())
        .orElseThrow(() -> new BaseException(BaseResponseMessage.MEMBER_NOT_FOUND));
        Optional<TotalProcess> result = totalProcessRepository.findByAnnouncementIdxAndSeekerIdx(dto.getAnnouncementIdx(), dto.getSeekerIdx());
        Boolean isPassFlag = (dto.getIsPass() == 1) ? true : false;
        if(result.isPresent()){
            TotalProcess totalProcess = result.get();
            if(dto.getInterviewNum() == 0) {
                if(!isPassFlag){ // 불합격하면
                    totalProcess.setResumeResult(isPassFlag);
                    totalProcess.setInterviewOneResult(isPassFlag);
                    totalProcess.setInterviewTwoResult(isPassFlag);
                    totalProcess.setFinalResult(isPassFlag);
                } else { // 합격하면

                    // 만약 채용담당자가 1차 2차 결과가 정해졌고, 지원서 결과만 수정하려고 할때와 진행상황이 지원서 부분일때
                    if(result.get().getInterviewOneResult() != null && result.get().getInterviewTwoResult() != null && result.get().getFinalResult() != null){
                        totalProcess.setResumeResult(isPassFlag);
                    } else {
                        totalProcess.setResumeResult(isPassFlag);
                        totalProcess.setInterviewOneResult(null);
                        totalProcess.setInterviewTwoResult(null);
                        totalProcess.setFinalResult(null);
                    }
                }
            } else if(dto.getInterviewNum() == 1) {
                if(!isPassFlag){
                    totalProcess.setInterviewOneResult(isPassFlag);
                    totalProcess.setInterviewTwoResult(isPassFlag);
                    totalProcess.setFinalResult(isPassFlag);
                } else { // 합격하면
                    // 만약 채용담당자가 2차 결과가 정해졌고, 1차 면접 결과만 수정하려고 할때와 진행상황이 지원서 부분일때
                    if(result.get().getInterviewTwoResult() != null && result.get().getFinalResult() != null){
                        totalProcess.setInterviewOneResult(isPassFlag);
                    } else {
                        totalProcess.setInterviewOneResult(isPassFlag);
                        totalProcess.setInterviewTwoResult(null);
                        totalProcess.setFinalResult(null);
                    }
                }
            }  else if (dto.getInterviewNum() == 2) { // 2차 합격시 최종합격 불합
                if(!isPassFlag){
                    totalProcess.setInterviewTwoResult(isPassFlag);
                    totalProcess.setFinalResult(isPassFlag);
                } else {
                    totalProcess.setInterviewTwoResult(isPassFlag);
                    totalProcess.setFinalResult(isPassFlag);
                }
            }
            totalProcess.setAnnouncement(announcement);
            totalProcess.setSeeker(seeker);
            totalProcessRepository.save(totalProcess);
            return TotalProcessCreateRes.builder().idx(totalProcess.getIdx()).build();
        }else {
            // 전체 지원 과정이 없을때, 대부분 지원서 단계에서 걸림 -> 지원서 결과가 없다면 1차, 2차, 최종 결과는 DB 임의 조작의 경우만 있음
            TotalProcess totalProcess = null;
            // 서류에 불합격
            if(dto.getInterviewNum() == 0) {
                totalProcess = TotalProcess.builder()
                        .resumeResult(isPassFlag)
                        .announcement(announcement)
                        .seeker(seeker)
                        .build();
            } else if(dto.getInterviewNum() == 1) {
                totalProcess = TotalProcess.builder()
                        .interviewOneResult(isPassFlag)
                        .announcement(announcement)
                        .seeker(seeker)
                        .build();
            }  else if (dto.getInterviewNum() == 2) {
                totalProcess = TotalProcess.builder()
                        .interviewTwoResult(isPassFlag)
                        .finalResult(isPassFlag)
                        .announcement(announcement)
                        .seeker(seeker)
                           .build();
            } else if (dto.getInterviewNum() == 3) {
                totalProcess = TotalProcess.builder()
                        .finalResult(isPassFlag)
                        .announcement(announcement)
                        .seeker(seeker)
                        .build();
            } else {
                throw new BaseException(BaseResponseMessage.TOTAL_PROCESS_CREATE_FAIL);
            }
            totalProcessRepository.save(totalProcess);
            return TotalProcessCreateRes.builder().idx(totalProcess.getIdx()).build();
        }
    }

    @Transactional
    public Page<TotalProcessReadAllRes> readAll(CustomUserDetails customUserDetails, Long announcementIdx, Integer page, Integer size) throws BaseException {
        Long recruiterIdx = customUserDetails.getIdx();
        // 채용담당자 테이블 조회
        Optional<Recruiter> resultRecruiter = recruiterRepository.findByRecruiterIdx(recruiterIdx);
        if(resultRecruiter.isPresent()) {
            // 공고 테이블 조회 , 공고 idx가 채용담당자가 등록한 공고가 맞는지
            Optional<Announcement> resultAnnouncement = announcementRepository.findByAnnounceIdx(announcementIdx);
            if(resultAnnouncement.isPresent()) {
                if(!resultAnnouncement.get().getRecruiter().getIdx().equals(recruiterIdx)) {
                    throw new BaseException(BaseResponseMessage.ACCESS_DENIED);
                }
            }

            // 전체 프로세스 테이블 조회 (페이징 처리)
            Pageable pageable = PageRequest.of(page, size);
            Page<TotalProcess> resultTotalProcesses = totalProcessRepository.findAllByAnnouncementIdx(announcementIdx, pageable);

            if(resultTotalProcesses.hasContent()) {
                List<TotalProcessReadAllRes> totalProcessReadAllResList = new ArrayList<>();
                for(TotalProcess totalProcess : resultTotalProcesses) {
                    totalProcessReadAllResList.add(TotalProcessReadAllRes.builder()
                            .totalProcessIdx(totalProcess.getIdx())
                            .resumeResult(totalProcess.getResumeResult())
                            .interviewOneResult(totalProcess.getInterviewOneResult())
                            .interviewTwoResult(totalProcess.getInterviewTwoResult())
                            .finalResult(totalProcess.getFinalResult())
                            .seekerName(totalProcess.getSeeker().getName())
                            .build());
                }

                return new PageImpl<>(totalProcessReadAllResList, pageable, resultTotalProcesses.getTotalElements());
            }

            throw new BaseException(BaseResponseMessage.TOTAL_PROCESS_READ_FAIL);
        }
        throw new BaseException(BaseResponseMessage.RESUME_REGISTER_FAIL_NOT_FOUND_ANNOUNCE);
    }
}
