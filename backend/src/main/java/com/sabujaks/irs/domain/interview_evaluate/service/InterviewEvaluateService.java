package com.sabujaks.irs.domain.interview_evaluate.service;

import com.sabujaks.irs.domain.announcement.model.entity.Announcement;
import com.sabujaks.irs.domain.announcement.repository.AnnouncementRepository;
import com.sabujaks.irs.domain.interview_evaluate.model.entity.InterviewEvaluateForm;
import com.sabujaks.irs.domain.interview_evaluate.model.request.InterviewEvaluateFormCreateReq;
import com.sabujaks.irs.domain.interview_evaluate.model.response.InterviewEvaluateFormCreateRes;
import com.sabujaks.irs.domain.interview_evaluate.repository.InterviewEvaluateFormRepository;
import com.sabujaks.irs.domain.interview_evaluate.repository.InterviewEvaluateRepository;
import com.sabujaks.irs.domain.interview_evaluate.repository.InterviewEvaluateResultRepository;
import com.sabujaks.irs.global.common.exception.BaseException;
import com.sabujaks.irs.global.common.responses.BaseResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InterviewEvaluateService {
    private final InterviewEvaluateRepository interviewEvaluateRepository;
    private final InterviewEvaluateFormRepository interviewEvaluateFormRepository;
    private final InterviewEvaluateResultRepository interviewEvaluateResultRepository;
    private final AnnouncementRepository announcementRepository;

    public InterviewEvaluateFormCreateRes createForm (InterviewEvaluateFormCreateReq dto) throws BaseException {
        Announcement announcement = announcementRepository.findByAnnounceIdx(dto.getAnnounceIdx())
        .orElseThrow(() -> new BaseException(BaseResponseMessage.ANNOUNCEMENT_SEARCH_FAIL));
        Optional<InterviewEvaluateForm> result = interviewEvaluateRepository.findByAnnounceIdx(dto.getAnnounceIdx());
        if(result.isEmpty()){
            InterviewEvaluateForm interviewEvaluateForm = InterviewEvaluateForm.builder()
                    .announcement(announcement)
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
            return InterviewEvaluateFormCreateRes.builder()
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
            return InterviewEvaluateFormCreateRes.builder()
                    .idx(interviewEvaluateForm.getIdx())
                    .build();
        }
    }
}
