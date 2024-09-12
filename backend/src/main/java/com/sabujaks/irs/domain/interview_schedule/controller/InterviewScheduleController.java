package com.sabujaks.irs.domain.interview_schedule.controller;

import com.sabujaks.irs.domain.interview_schedule.model.request.InterviewScheduleReq;
import com.sabujaks.irs.domain.interview_schedule.model.response.InterviewScheduleListsRes;
import com.sabujaks.irs.domain.interview_schedule.model.response.InterviewScheduleRes;
import com.sabujaks.irs.domain.interview_schedule.service.InterviewScheduleService;
import com.sabujaks.irs.global.common.exception.BaseException;
import com.sabujaks.irs.global.common.responses.BaseResponse;
import com.sabujaks.irs.global.common.responses.BaseResponseMessage;
import com.sabujaks.irs.global.utils.EmailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/interview-schedule")
@RequiredArgsConstructor
public class InterviewScheduleController {

    private final InterviewScheduleService interviewScheduleService;
    private final EmailSender emailSender;

    @PostMapping("/create")
    public ResponseEntity<BaseResponse<InterviewScheduleReq>> create (
            @RequestBody InterviewScheduleReq dto) throws BaseException {

        InterviewScheduleListsRes response = interviewScheduleService.create(dto);
        emailSender.sendEmail(response, dto.getInterviewerList());


        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.INTERVIEW_SCHEDULE_CREATE_SUCCESS, response));
    }

    @GetMapping("/read-all/exp")
    public ResponseEntity<BaseResponse<?>> readAllExp () throws BaseException {
//        InterviewScheduleRes response = interviewScheduleService.readAllExp();

        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.INTERVIEW_SCHEDULE_CREATE_SUCCESS));
    }



}
