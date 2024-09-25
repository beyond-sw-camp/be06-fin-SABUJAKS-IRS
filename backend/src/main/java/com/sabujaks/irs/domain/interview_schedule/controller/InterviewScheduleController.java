package com.sabujaks.irs.domain.interview_schedule.controller;

import com.sabujaks.irs.domain.interview_schedule.model.request.InterviewScheduleReq;
import com.sabujaks.irs.domain.interview_schedule.model.request.InterviewScheduleUpdateReq;
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
            @RequestParam Long announcementIdx,
            @RequestParam Integer pageNum) throws BaseException {
        List<InterviewScheduleRes> response = interviewScheduleService.readAll(careerBase, announcementIdx, pageNum);

        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.INTERVIEW_SCHEDULE_READ_ALL_SUCCESS, response));
    }

    @GetMapping("/read-all/count")
    public ResponseEntity<BaseResponse<?>> getTotalInterviewSchedule (
            @RequestParam String careerBase,
            @RequestParam Long announcementIdx) throws BaseException {
        Integer response = interviewScheduleService.getTotalInterviewSchedule(careerBase, announcementIdx);

        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.INTERVIEW_SCHEDULE_READ_ALL_SUCCESS, response));
    }

    @GetMapping("/read")
    public ResponseEntity<BaseResponse<?>> read (
            @RequestParam Long interviewScheduleIdx) throws BaseException {
        InterviewScheduleRes response = interviewScheduleService.read(interviewScheduleIdx);

        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.INTERVIEW_SCHEDULE_READ_ALL_SUCCESS, response));
    }

    @PostMapping("/update")
    public ResponseEntity<BaseResponse<InterviewScheduleUpdateReq>> create (
            @RequestBody InterviewScheduleUpdateReq dto) throws BaseException {
        interviewScheduleService.update(dto);

        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.INTERVIEW_SCHEDULE_UPDATE_SUCCESS));
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
            @RequestParam Long announcementIdx,
            @RequestParam Integer pageNum) throws BaseException {
        List<ReScheduleRes> response = interviewScheduleService.readAllReSchedule(announcementIdx, pageNum);

        return ResponseEntity.ok(new BaseResponse<>(BaseResponseMessage.RESCHEDULE_SEARCH_ALL_SUCCESS, response));
    }

    @GetMapping("/read-all/count/re-schedule")
    public ResponseEntity<BaseResponse<?>> getTotalReSchedule(
            @RequestParam Long announcementIdx) throws BaseException {
        Integer response = interviewScheduleService.getTotalReSchedule(announcementIdx);

        return ResponseEntity.ok(new BaseResponse<>(BaseResponseMessage.RESCHEDULE_SEARCH_ALL_SUCCESS, response));
    }
}
