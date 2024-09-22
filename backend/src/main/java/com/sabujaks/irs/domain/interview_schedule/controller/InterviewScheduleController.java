package com.sabujaks.irs.domain.interview_schedule.controller;

import com.sabujaks.irs.domain.interview_schedule.model.request.InterviewScheduleReq;
import com.sabujaks.irs.domain.interview_schedule.model.request.ReScheduleReq;
import com.sabujaks.irs.domain.interview_schedule.model.response.InterviewScheduleRes;
import com.sabujaks.irs.domain.interview_schedule.model.response.ReScheduleRes;
import com.sabujaks.irs.domain.interview_schedule.service.InterviewScheduleService;
import com.sabujaks.irs.global.common.exception.BaseException;
import com.sabujaks.irs.global.common.responses.BaseResponse;
import com.sabujaks.irs.global.common.responses.BaseResponseMessage;
import com.sabujaks.irs.global.security.CustomUserDetails;
import com.sabujaks.irs.global.utils.EmailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/interview-schedule")
@RequiredArgsConstructor
public class InterviewScheduleController {

    private final InterviewScheduleService interviewScheduleService;
    private final EmailSender emailSender;

    @PostMapping("/create")
    public ResponseEntity<BaseResponse<InterviewScheduleReq>> create (
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody InterviewScheduleReq dto) throws BaseException {
        InterviewScheduleRes response = interviewScheduleService.create(customUserDetails, dto);
        emailSender.sendEmail(response, dto.getEstimatorList());
        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.INTERVIEW_SCHEDULE_CREATE_SUCCESS, response));
    }

    @GetMapping("/read-all")
    public ResponseEntity<BaseResponse<?>> readAll (
            @RequestParam String careerBase,
            @RequestParam Long announcementIdx){
        List<InterviewScheduleRes> response = interviewScheduleService.readAll(careerBase, announcementIdx);

        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.INTERVIEW_SCHEDULE_READ_ALL_SUCCESS, response));
    }

    // CustomUserDetail 추가하기
    @PostMapping("/create/re-schedule")
    public ResponseEntity<BaseResponse<ReScheduleReq>> createReSchedule (
            @RequestBody ReScheduleReq dto) throws BaseException {
        ReScheduleRes response = interviewScheduleService.createReSchedule(dto);

//        emailSender.sendEmail(response, dto.getEstimatorList());
        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.RESCHEDULE_CREATE_SUCCESS, response));
    }

    @GetMapping("/read-all/re-schedule")
    public ResponseEntity<BaseResponse<?>> readAllReSchedule(
            @RequestParam Long announcementIdx) throws BaseException {
        List<ReScheduleRes> response = interviewScheduleService.readAllReSchedule(announcementIdx);

        return ResponseEntity.ok(new BaseResponse<>(BaseResponseMessage.RESCHEDULE_SEARCH_ALL_SUCCESS, response));
    }
}
