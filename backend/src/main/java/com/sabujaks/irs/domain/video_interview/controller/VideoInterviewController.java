package com.sabujaks.irs.domain.video_interview.controller;

import com.sabujaks.irs.domain.video_interview.mdoel.request.VideoInterviewCreateReq;
import com.sabujaks.irs.domain.video_interview.mdoel.request.VideoInterviewTokenGetReq;
import com.sabujaks.irs.domain.video_interview.mdoel.response.VideoInterviewCreateRes;
import com.sabujaks.irs.domain.video_interview.mdoel.response.VideoInterviewSearchRes;
import com.sabujaks.irs.domain.video_interview.mdoel.response.VideoInterviewTokenGetRes;
import com.sabujaks.irs.domain.video_interview.service.VideoInterviewService;
import com.sabujaks.irs.global.common.exception.BaseException;
import com.sabujaks.irs.global.common.responses.BaseResponse;
import com.sabujaks.irs.global.common.responses.BaseResponseMessage;
import io.openvidu.java.client.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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
        @RequestParam String announceUUID) throws BaseException {
        List<VideoInterviewSearchRes> response = videoInterviewService.searchAll(announceUUID);
        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.VIDEO_INTERVIEW_SEARCH_ALL_SUCCESS, response));
    }

    @PostMapping("/token")
    public ResponseEntity<BaseResponse> createConnection(
        @RequestBody VideoInterviewTokenGetReq dto) throws OpenViduJavaClientException, OpenViduHttpException, BaseException {
        VideoInterviewTokenGetRes response = videoInterviewService.getToken(dto);
        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.VIDEO_INTERVIEW_JOIN_SUCCESS, response.getToken()));
    }
}
