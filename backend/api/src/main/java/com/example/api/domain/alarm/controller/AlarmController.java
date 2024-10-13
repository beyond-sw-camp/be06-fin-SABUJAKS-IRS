package com.example.api.domain.alarm.controller;

import com.example.api.domain.alarm.model.request.AlarmReq;
import com.example.api.domain.alarm.model.response.AlarmRes;
import com.example.api.domain.alarm.service.AlarmService;
import com.example.api.global.common.exception.BaseException;
import com.example.api.global.common.responses.BaseResponse;
import com.example.api.global.common.responses.BaseResponseMessage;
import com.example.api.global.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@RestController
@RequestMapping("/api/alarm")
@RequiredArgsConstructor
public class AlarmController {

    private final AlarmService alarmService;

//    @GetMapping(value = "/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//    public SseEmitter subscribe(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
//        Long seekerIdx = customUserDetails.getIdx();
//        return alarmService.subscribe(seekerIdx);
//    }

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
