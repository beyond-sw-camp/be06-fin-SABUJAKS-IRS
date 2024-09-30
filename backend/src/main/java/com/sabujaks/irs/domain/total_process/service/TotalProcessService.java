package com.sabujaks.irs.domain.total_process.service;

import com.sabujaks.irs.domain.announcement.model.entity.Announcement;
import com.sabujaks.irs.domain.announcement.repository.AnnouncementRepository;
import com.sabujaks.irs.domain.auth.model.entity.Seeker;
import com.sabujaks.irs.domain.auth.repository.SeekerRepository;
import com.sabujaks.irs.domain.total_process.model.entity.TotalProcess;
import com.sabujaks.irs.domain.total_process.model.request.TotalProcessCreateReq;
import com.sabujaks.irs.domain.total_process.model.response.TotalProcessCreateRes;
import com.sabujaks.irs.domain.total_process.repository.TotalProcessRepository;
import com.sabujaks.irs.global.common.exception.BaseException;
import com.sabujaks.irs.global.common.responses.BaseResponseMessage;
import com.sabujaks.irs.global.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TotalProcessService {
    private final AnnouncementRepository announcementRepository;
    private final SeekerRepository seekerRepository;
    private final TotalProcessRepository totalProcessRepository;

    public TotalProcessCreateRes create(TotalProcessCreateReq dto, CustomUserDetails customUserDetails) throws BaseException {
        Announcement announcement = announcementRepository.findByAnnounceIdx(dto.getAnnouncementIdx())
        .orElseThrow(() -> new BaseException(BaseResponseMessage.ANNOUNCEMENT_SEARCH_FAIL_NOT_FOUND));
        if(!Objects.equals(announcement.getRecruiter().getIdx(), customUserDetails.getRecruiter().getIdx())) {
            throw new BaseException(BaseResponseMessage.ANNOUNCEMENT_SEARCH_FAIL_INVALID_REQUEST);
        }
        Seeker seeker = seekerRepository.findBySeekerIdx(dto.getSeekerIdx())
        .orElseThrow(() -> new BaseException(BaseResponseMessage.MEMBER_NOT_FOUND));
        Optional<TotalProcess> result = totalProcessRepository.findByAnnouncementIdxAndSeekerIdx(dto.getAnnouncementIdx(), dto.getSeekerIdx());
        Boolean isPassFlag = dto.getIsPass() == 1;
        if(result.isPresent()){
            TotalProcess totalProcess = result.get();
            if(dto.getInterviewNum() == 0) {
                totalProcess.setResumeResult(isPassFlag);
            } else if(dto.getInterviewNum() == 1) {
                totalProcess.setInterviewOneResult(isPassFlag);
            }  else if (dto.getInterviewNum() == 2) {
                totalProcess.setInterviewTwoResult(isPassFlag);
            } else if (dto.getInterviewNum() == 3) {
                totalProcess.setInterviewTwoResult(isPassFlag);
            }
            totalProcess.setAnnouncement(announcement);
            totalProcess.setSeeker(seeker);
            return TotalProcessCreateRes.builder()
                    .idx(totalProcess.getIdx())
                    .build();
        }else {
            TotalProcess totalProcess = null;
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
                        .interviewOneResult(isPassFlag)
                        .announcement(announcement)
                        .seeker(seeker)
                        .build();
            } else if (dto.getInterviewNum() == 3) {
                totalProcess = TotalProcess.builder()
                        .interviewOneResult(isPassFlag)
                        .announcement(announcement)
                        .seeker(seeker)
                        .build();
            } else {
                throw new BaseException(BaseResponseMessage.TOTAL_PROCESS_CREATE_FAIL);
            }
            totalProcessRepository.save(totalProcess);
            return TotalProcessCreateRes.builder()
                    .idx(totalProcess.getIdx())
                    .build();
        }
    }
}
