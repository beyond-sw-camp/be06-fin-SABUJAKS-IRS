package com.sabujaks.irs.domain.interview_evaluate.controller;

import com.sabujaks.irs.domain.interview_evaluate.model.request.InterviewEvaluateFormCreateReq;
import com.sabujaks.irs.domain.interview_evaluate.model.response.InterviewEvaluateFormCreateRes;
import com.sabujaks.irs.domain.interview_evaluate.model.response.InterviewEvaluateFormReadRes;
import com.sabujaks.irs.domain.interview_evaluate.service.InterviewEvaluateService;
import com.sabujaks.irs.global.common.exception.BaseException;
import com.sabujaks.irs.global.common.responses.BaseResponse;
import com.sabujaks.irs.global.common.responses.BaseResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/interview-evaluate")
public class InterviewEvaluateController {
    private final InterviewEvaluateService interviewEvaluateService;

    @PostMapping("/create-form")
    public ResponseEntity<BaseResponse> createForm(
        @RequestBody InterviewEvaluateFormCreateReq dto) throws BaseException {
        InterviewEvaluateFormCreateRes response = interviewEvaluateService.createForm(dto);
        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.INTERVIEW_EVALUATE_CREATE_FORM_SUCCESS, response));
    }

    @GetMapping("/search-form")
    public ResponseEntity<BaseResponse> searchForm(
        @RequestParam String announceUUID) throws BaseException {
        InterviewEvaluateFormReadRes response = interviewEvaluateService.searchForm(announceUUID);
        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.INTERVIEW_EVALUATE_SEARCH_FORM_SUCCESS, response));
    }
}
