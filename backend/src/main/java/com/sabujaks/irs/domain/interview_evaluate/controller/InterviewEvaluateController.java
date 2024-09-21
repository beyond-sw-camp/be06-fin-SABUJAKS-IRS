package com.sabujaks.irs.domain.interview_evaluate.controller;

import com.sabujaks.irs.domain.interview_evaluate.model.request.InterviewEvaluateFormCreateReq;
import com.sabujaks.irs.domain.interview_evaluate.model.response.InterviewEvaluateFormCreateRes;
import com.sabujaks.irs.domain.interview_evaluate.model.response.InterviewEvaluateFormReadRes;
import com.sabujaks.irs.domain.interview_evaluate.service.InterviewEvaluateService;
import com.sabujaks.irs.global.common.exception.BaseException;
import com.sabujaks.irs.global.common.responses.BaseResponse;
import com.sabujaks.irs.global.common.responses.BaseResponseMessage;
import com.sabujaks.irs.global.security.CustomUserDetails;
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
}
