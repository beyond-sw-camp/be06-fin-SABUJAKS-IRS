package com.sabujaks.irs.domain.video_interview.controller;

import com.sabujaks.irs.domain.video_interview.model.request.VideoInterviewCreateReq;
import com.sabujaks.irs.domain.video_interview.model.request.VideoInterviewTokenGetReq;
import com.sabujaks.irs.domain.video_interview.model.response.VideoInterviewCreateRes;
import com.sabujaks.irs.domain.video_interview.model.response.VideoInterviewSearchRes;
import com.sabujaks.irs.domain.video_interview.model.response.VideoInterviewTokenGetRes;
import com.sabujaks.irs.domain.video_interview.service.VideoInterviewService;
import com.sabujaks.irs.global.common.exception.BaseException;
import com.sabujaks.irs.global.common.responses.BaseResponse;
import com.sabujaks.irs.global.common.responses.BaseResponseMessage;
import com.sabujaks.irs.global.security.CustomUserDetails;
import io.openvidu.java.client.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/video-interview")
@RequiredArgsConstructor
public class VideoInterviewController {
    private final VideoInterviewService videoInterviewService;

    @PostMapping("/create")
    public ResponseEntity<BaseResponse<VideoInterviewCreateRes>> create(
        @RequestBody VideoInterviewCreateReq dto) throws OpenViduJavaClientException, OpenViduHttpException, BaseException {
        VideoInterviewCreateRes response = videoInterviewService.create(dto);
        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.VIDEO_INTERVIEW_CREATE_SUCCESS, response));
    }

    @GetMapping("/search-all")
    public ResponseEntity<BaseResponse<VideoInterviewSearchRes>> searchAll(
        @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @RequestParam String announceUUID) throws BaseException {
        List<VideoInterviewSearchRes> response = videoInterviewService.searchAll(announceUUID, customUserDetails);
        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.VIDEO_INTERVIEW_SEARCH_ALL_SUCCESS, response));
    }

    @PostMapping("/get-session-token")
    public ResponseEntity<BaseResponse> sessionToken(
        @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @RequestBody VideoInterviewTokenGetReq dto) throws OpenViduJavaClientException, OpenViduHttpException, BaseException {
        VideoInterviewTokenGetRes response = videoInterviewService.sessionToken(dto, customUserDetails);
        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.VIDEO_INTERVIEW_JOIN_SUCCESS, response));
    }

}
