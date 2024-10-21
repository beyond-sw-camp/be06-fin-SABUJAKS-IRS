package com.example.api.domain.interview_evaluate.controller;

import com.example.api.domain.interview_evaluate.model.request.InterviewEvaluateCreateReq;
import com.example.api.domain.interview_evaluate.model.request.InterviewEvaluateFormCreateReq;
import com.example.api.domain.interview_evaluate.model.response.*;
import com.example.api.domain.interview_evaluate.service.InterviewEvaluateService;
import com.example.api.global.common.exception.BaseException;
import com.example.api.global.common.responses.BaseResponse;
import com.example.api.global.common.responses.BaseResponseMessage;
import com.example.api.global.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/interview-evaluate")
public class InterviewEvaluateController {
    private final InterviewEvaluateService interviewEvaluateService;

    @PostMapping("/create-form")
    public ResponseEntity<BaseResponse> createForm(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody InterviewEvaluateFormCreateReq dto) throws BaseException {
        InterviewEvaluateFormCreateRes response = interviewEvaluateService.createForm(customUserDetails, dto);
        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.INTERVIEW_EVALUATE_CREATE_FORM_SUCCESS, response));
    }
    
    @GetMapping("/search-form")
    public ResponseEntity<BaseResponse> searchForm(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam String announcementUUID,
            @RequestParam(required = false) String interviewScheduleUUID) throws BaseException {
        InterviewEvaluateFormReadRes response = interviewEvaluateService.searchForm(customUserDetails, announcementUUID, interviewScheduleUUID);
        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.INTERVIEW_EVALUATE_SEARCH_FORM_SUCCESS, response));
    }

    @GetMapping("/read-all/resume-info")
    public ResponseEntity<BaseResponse<InterviewEvaluateReadAllResumeInfo>> readAllResumeInfo(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam String announcementUUID,
            @RequestParam String interviewScheduleUUID) throws BaseException {
        InterviewEvaluateReadAllResumeInfo response = interviewEvaluateService.readAllResumeInfo(customUserDetails, announcementUUID, interviewScheduleUUID);
        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.INTERVIEW_EVALUATE_SEARCH_RESUME_SUCCESS, response));
    }

    @PostMapping("/create-evaluate")
    public ResponseEntity<BaseResponse<InterviewEvaluateCreateRes>> createEvaluate(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody InterviewEvaluateCreateReq dto) throws BaseException {
        InterviewEvaluateCreateRes response = interviewEvaluateService.createEvaluate(customUserDetails, dto);
        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.INTERVIEW_EVALUATE_CREATE_SUCCESS, response));
    }

    @GetMapping("/read-all/evaluate")
    public ResponseEntity<BaseResponse<InterviewEvaluateReadAllRes>> readAllEvaluate (
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
                @RequestParam Integer interviewNum,
            @RequestParam Long announceIdx)throws BaseException {
        InterviewEvaluateReadAllRes response = interviewEvaluateService.readAllEvaluate(customUserDetails, announceIdx, interviewNum);
        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.INTERVIEW_EVALUATE_READ_ALL_SUCCESS, response));
    }

}
