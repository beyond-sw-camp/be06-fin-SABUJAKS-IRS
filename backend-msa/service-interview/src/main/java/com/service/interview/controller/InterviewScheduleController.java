package com.service.interview.controller;

import com.service.common.base.BaseException;
import com.service.common.base.BaseResponse;
import com.service.common.base.BaseResponseMessage;
import com.service.common.dto.feign.ReadSeekerRes;
import com.service.common.dto.request.interview.CreateAllInterviewReScheduleReq;
import com.service.common.dto.request.interview.CreateInterviewReScheduleReq;
import com.service.common.dto.request.interview.CreateInterviewScheduleReq;
import com.service.common.dto.response.interview.*;
import com.service.interview.service.InterviewScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/interview/schedule")
@RequiredArgsConstructor
public class InterviewScheduleController {

    private final InterviewScheduleService interviewScheduleService;
//    private final EmailSenderSeeker emailSenderSeeker;

    @PostMapping("/create")
    public ResponseEntity<BaseResponse<CreateInterviewScheduleRes>> create (
        @RequestHeader("X-User-Idx") Long memberIdx,
        @RequestHeader("X-User-Email") String memberEmail,
        @RequestHeader("X-User-Role") String memberRole,
        @RequestBody CreateInterviewScheduleReq dto) throws BaseException {
        CreateInterviewScheduleRes response = interviewScheduleService.create(memberIdx, memberEmail, memberRole, dto);
//        emailSenderSeeker.sendNotiInterviewScheduleEmail(response);
        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.INTERVIEW_SCHEDULE_CREATE_SUCCESS, response));
    }

    @GetMapping("/read-all")
    public ResponseEntity<BaseResponse<?>> readAll (
        @RequestHeader("X-User-Idx") Long memberIdx,
        @RequestHeader("X-User-Email") String memberEmail,
        @RequestHeader("X-User-Role") String memberRole,
        @RequestParam String careerBase,
        @RequestParam Long announcementIdx,
        @RequestParam Integer pageNum) throws BaseException {
        List<ReadInterviewScheduleRes> response = interviewScheduleService.readAll(memberIdx, memberEmail, memberRole, careerBase, announcementIdx, pageNum);
        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.INTERVIEW_SCHEDULE_READ_ALL_SUCCESS, response));
    }

    @GetMapping("/read/schedule-size")
    public ResponseEntity<BaseResponse<?>> getTotalAmount (
        @RequestParam String careerBase,
        @RequestParam Long announcementIdx) throws BaseException {
        Integer response = interviewScheduleService.getTotalScheduleSize(careerBase, announcementIdx);
        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.INTERVIEW_SCHEDULE_READ_ALL_SUCCESS, response));
    }

    @GetMapping("/read")
    public ResponseEntity<BaseResponse<?>> read (
        @RequestParam Long interviewScheduleIdx) throws BaseException {
        ReadInterviewScheduleRes response = interviewScheduleService.read(interviewScheduleIdx);
        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.INTERVIEW_SCHEDULE_READ_ALL_SUCCESS, response));
    }

    @PostMapping("/update")
    public ResponseEntity<BaseResponse<CreateAllInterviewReScheduleReq>> create (
        @RequestBody CreateAllInterviewReScheduleReq dto) throws BaseException {
        interviewScheduleService.update(dto);
        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.INTERVIEW_SCHEDULE_UPDATE_SUCCESS));
    }

    @PostMapping("/create/reschedule")
    public ResponseEntity<BaseResponse<CreateInterviewReScheduleRes>> createReSchedule (
        @RequestHeader("X-User-Idx") Long memberIdx,
        @RequestHeader("X-User-Email") String memberEmail,
        @RequestHeader("X-User-Role") String memberRole,
        @RequestBody CreateInterviewReScheduleReq dto) throws BaseException {
        CreateInterviewReScheduleRes response = interviewScheduleService.createReSchedule(memberIdx, memberEmail, memberRole, dto);

//        emailSender.sendEmail(response, dto.getEstimatorList());
        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.RESCHEDULE_CREATE_SUCCESS, response));
    }

    @GetMapping("/read-all/reschedule")
    public ResponseEntity<BaseResponse<?>> readAllReSchedule(
        @RequestParam Long announcementIdx,
        @RequestParam Integer pageNum) throws BaseException {
        List<ReadInterviewReScheduleRes> response = interviewScheduleService.readAllReSchedule(announcementIdx, pageNum);

        return ResponseEntity.ok(new BaseResponse<>(BaseResponseMessage.RESCHEDULE_SEARCH_ALL_SUCCESS, response));
    }

    @GetMapping("/read/reschedule-size")
    public ResponseEntity<BaseResponse<?>> getTotalReSchedule(
        @RequestParam Long announcementIdx) throws BaseException {
        Integer response = interviewScheduleService.getTotalReScheduleSize(announcementIdx);
        return ResponseEntity.ok(new BaseResponse<>(BaseResponseMessage.RESCHEDULE_SEARCH_ALL_SUCCESS, response));
    }

//    @GetMapping("/read-all/seeker")
//    public ResponseEntity<BaseResponse<?>> getSeekerList(
//        @RequestParam Long announcementIdx) throws BaseException {
//        List<ReadSeekerRes> response = interviewScheduleService.getSeekerList(announcementIdx);
//        return ResponseEntity.ok(new BaseResponse<>(BaseResponseMessage.AUTH_SEARCH_USER_INFO_SUCCESS, response));
//    }

    @GetMapping("/read-all/available-time")
    public ResponseEntity<BaseResponse<?>> readAvailableTime(
        @RequestParam String interviewDate,
        @RequestParam Long teamIdx,
        @RequestParam Long announcementIdx) throws BaseException {
        List<ReadAvailableTimeRes> response = interviewScheduleService.readAvailableTime(interviewDate, teamIdx, announcementIdx);
        return ResponseEntity.ok(new BaseResponse<>(BaseResponseMessage.INTERVIEW_SCHEDULE_AVAILABLE_TIMES_READ_SUCCESS, response));
    }
}
