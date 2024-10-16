package com.service.interview.controller;

import com.service.common.base.BaseException;
import com.service.common.base.BaseResponse;
import com.service.common.base.BaseResponseMessage;
import com.service.common.dto.request.interview.CreateInterviewEvaluateFormReq;
import com.service.common.dto.request.interview.CreateInterviewEvaluateReq;
import com.service.common.dto.response.interview.*;
import com.service.interview.service.InterviewEvaluateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/interview/evaluate")
public class InterviewEvaluateController {

    private final InterviewEvaluateService interviewEvaluateService;

    @PostMapping("/create/form")
    public ResponseEntity<BaseResponse> createForm(
        @RequestHeader("X-User-Idx") Long memberIdx,
        @RequestHeader("X-User-Email") String memberEmail,
        @RequestHeader("X-User-Role") String memberRole,
        @RequestBody CreateInterviewEvaluateFormReq dto) throws BaseException {
        CreateInterviewEvaluateFormRes response = interviewEvaluateService.createForm(memberIdx, memberEmail, memberRole, dto);
        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.INTERVIEW_EVALUATE_CREATE_FORM_SUCCESS, response));
    }

    @GetMapping("/read/form")
    public ResponseEntity<BaseResponse> readForm(
        @RequestHeader("X-User-Idx") Long memberIdx,
        @RequestHeader("X-User-Email") String memberEmail,
        @RequestHeader("X-User-Role") String memberRole,
        @RequestParam String announcementUUID,
        @RequestParam(required = false) String interviewScheduleUUID) throws BaseException {
        ReadInterviewEvaluateFormRes response = interviewEvaluateService.readForm(memberIdx, memberRole, memberEmail, announcementUUID, interviewScheduleUUID);
        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.INTERVIEW_EVALUATE_SEARCH_FORM_SUCCESS, response));
    }

    @GetMapping("/readMember-all/resume-info")
    public ResponseEntity<BaseResponse<ReadAllSubmissionResumeRes>> readAllResumeInfo(
            @RequestHeader("X-User-Idx") Long memberIdx,
            @RequestHeader("X-User-Email") String memberEmail,
            @RequestHeader("X-User-Role") String memberRole,
            @RequestParam String announcementUUID,
            @RequestParam String interviewScheduleUUID) throws BaseException {
        ReadAllSubmissionResumeRes response = interviewEvaluateService.readAllSubmissionResume(memberIdx, memberRole, memberEmail, announcementUUID, interviewScheduleUUID);
        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.INTERVIEW_EVALUATE_SEARCH_RESUME_SUCCESS, response));
    }

    @PostMapping("/create-evaluate")
    public ResponseEntity<BaseResponse<CreateInterviewEvaluateRes>> createEvaluate(
            @RequestHeader("X-User-Idx") Long memberIdx,
            @RequestHeader("X-User-Email") String memberEmail,
            @RequestHeader("X-User-Role") String memberRole,
            @RequestBody CreateInterviewEvaluateReq dto) throws BaseException {
        CreateInterviewEvaluateRes response = interviewEvaluateService.createEvaluate(memberIdx, memberEmail, memberRole, dto);
        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.INTERVIEW_EVALUATE_CREATE_SUCCESS, response));
    }
//
    @GetMapping("/read-all/evaluate")
    public ResponseEntity<BaseResponse<ReadAllInterviewEvaluateRes>> readAllEvaluate (
        @RequestHeader("X-User-Idx") Long memberIdx,
        @RequestHeader("X-User-Email") String memberEmail,
        @RequestHeader("X-User-Role") String memberRole,
        @RequestParam Integer interviewNum,
        @RequestParam Long announcementIdx)throws BaseException {
        ReadAllInterviewEvaluateRes response = interviewEvaluateService.readAllEvaluate(memberIdx, memberEmail, memberRole, announcementIdx, interviewNum);
        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.INTERVIEW_EVALUATE_READ_ALL_SUCCESS, response));
    }
}
