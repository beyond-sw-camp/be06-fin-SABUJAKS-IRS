package com.sabujaks.irs.domain.alarm.controller;

import com.sabujaks.irs.domain.alarm.model.request.AlarmReq;
import com.sabujaks.irs.domain.alarm.model.response.AlarmRes;
import com.sabujaks.irs.domain.alarm.service.AlarmService;
import com.sabujaks.irs.domain.interview_schedule.model.request.InterviewScheduleReq;
import com.sabujaks.irs.domain.interview_schedule.model.response.InterviewScheduleRes;
import com.sabujaks.irs.global.common.exception.BaseException;
import com.sabujaks.irs.global.common.responses.BaseResponse;
import com.sabujaks.irs.global.common.responses.BaseResponseMessage;
import com.sabujaks.irs.global.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alarm")
@RequiredArgsConstructor
public class AlarmController {

    private final AlarmService alarmService;

    @PostMapping("/create")
    public ResponseEntity<BaseResponse<AlarmReq>> create (
            @RequestBody AlarmReq dto) throws BaseException {
        AlarmRes response = alarmService.create(dto);
        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.ALARM_REGISTER_SUCCESS, response));
    }

    @GetMapping("/read-all")
    public ResponseEntity<BaseResponse<?>> readAll (
            @AuthenticationPrincipal CustomUserDetails customUserDetails) throws BaseException {

        Long seekerIdx = customUserDetails.getIdx();
        List<AlarmRes> response = alarmService.readAll(seekerIdx);
        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.ALARM_SEARCH_SUCCESS, response));
    }

    // 알림 상태 업데이트
    @GetMapping("/update-status/{idx}")
    public ResponseEntity<BaseResponse<?>> updateStatus(@PathVariable Long idx) throws BaseException {
        Boolean response = alarmService.updateStatus (idx);

        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.ALARM_SEARCH_SUCCESS, response));
    }
}
