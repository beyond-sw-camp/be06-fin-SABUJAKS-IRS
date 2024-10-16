package com.service.interview.controller;

import com.service.common.base.BaseException;
import com.service.common.base.BaseResponse;
import com.service.common.base.BaseResponseMessage;
import com.service.common.dto.request.interview.CreateAllInterviewOnlineReq;
import com.service.common.dto.request.interview.CreateInterviewOnlineReq;
import com.service.common.dto.request.interview.ReadInterviewOlineTokenReq;
import com.service.common.dto.response.interview.CreateInterviewOnlineRes;
import com.service.common.dto.response.interview.ReadInterviewOnlineRes;
import com.service.common.dto.response.interview.ReadInterviewOnlineTokenRes;
import com.service.interview.service.InterviewOnlineService;
import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduJavaClientException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/interview/online")
@RequiredArgsConstructor
public class InterviewOnlineController {

    private final InterviewOnlineService interviewOnlineService;
//    private final EmailSenderSeeker emailSenderSeeker;

    @PostMapping("/create")
    public ResponseEntity<BaseResponse<CreateInterviewOnlineRes>> create(
        @RequestBody CreateInterviewOnlineReq dto) throws OpenViduJavaClientException, OpenViduHttpException, BaseException {
        CreateInterviewOnlineRes response = interviewOnlineService.create(dto);
        // TODO: NOTIFICIATION
//        emailSenderSeeker.sendConfirmInterviewScheduleEmail(response);
        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.VIDEO_INTERVIEW_CREATE_SUCCESS, response));
    }

    @GetMapping("/read-all")
    public ResponseEntity<BaseResponse<ReadInterviewOnlineRes>> readAll(
        @RequestHeader("X-User-Idx") Long memberIdx,
        @RequestHeader("X-User-Email") String memberEmail,
        @RequestHeader("X-User-Role") String memberRole,
        @RequestParam String announcementUUID) throws BaseException {
        List<ReadInterviewOnlineRes> response = interviewOnlineService.readAll(memberIdx, memberEmail, memberRole, announcementUUID);
        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.VIDEO_INTERVIEW_SEARCH_ALL_SUCCESS, response));
    }

    @PostMapping("/get-session-token")
    public ResponseEntity<BaseResponse> readOnlineToken(
        @RequestHeader("X-User-Idx") Long memberIdx,
        @RequestHeader("X-User-Email") String memberEmail,
        @RequestHeader("X-User-Role") String memberRole,
        @RequestBody ReadInterviewOlineTokenReq dto) throws OpenViduJavaClientException, OpenViduHttpException, BaseException {
        ReadInterviewOnlineTokenRes response = interviewOnlineService.readOnlineToken(memberIdx, memberEmail, memberRole, dto);
        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.VIDEO_INTERVIEW_JOIN_SUCCESS, response));
    }

//    @PostMapping("/create-all")
//    public ResponseEntity<BaseResponse<?>> scheduleInterviewRoomCreation(
//        @RequestBody CreateAllInterviewOnlineReq dto) throws BaseException, OpenViduJavaClientException, OpenViduHttpException, JsonProcessingException {
//        String response = interviewOnlineService.createAll(dto);
//        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.VIDEO_INTERVIEW_JOIN_SUCCESS, response));
//    }

}
